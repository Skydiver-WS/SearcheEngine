package searchengine.services.indexing.fullIndexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.site.SitesList;
import searchengine.config.status.Status;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.Index;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;
import searchengine.model.SQL.SiteInfo;
import searchengine.services.deleteDataDB.nosql.DeleteCashLemmasService;
import searchengine.services.indexing.core.check.indexing.ChangeStartIndexingService;
import searchengine.services.deleteDataDB.sql.DeleteDataService;
import searchengine.services.indexing.core.check.lifeThread.LifeThread;
import searchengine.services.indexing.core.lemma.LemmaService;
import searchengine.services.indexing.core.parse.ParseService;
import searchengine.services.stopIndexing.StopIndexingService;
import searchengine.services.indexing.core.handler.WriteDbService;

import java.util.*;

import static searchengine.services.indexing.core.check.lifeThread.LifeThread.addThread;
import static searchengine.services.indexing.core.check.lifeThread.LifeThread.removeThread;
/**
 * Данный класс запускает индексацию сайтов, удаление и запись в БД MySQL и REDIS.
 * Включённые зависимости:
 * @see SitesList - список сайтов указанных в конфигурационном файле;
 * @see DeleteDataService - сервис запускает удаление из БД;
 * @see WriteDbService - сервис запускает запись в базу данных. Промежуточный класс для записи и изменения данных, а так же передачи
 *                       готовых объектов для записи в БД;
 * @see ChangeStartIndexingService - сервис проверяет наличие живых потоков,
 *       для исключения запуска повторной индексации при запущенной текущей;
 * @see StopIndexingService - сервис останавливает индексацию.
 * @see ParseService - сервис запускает обход сайта на всю его глубину;
 * @see LemmaService - сервис запускает приведение слов к их исходной форме;
 * @see DeleteCashLemmasService - сервис удаляет данные из БД Redis.
 * @author Aleksandr Isaev
 */

@Service
public class IndexingImpl implements IndexingService {
    @Autowired
    private SitesList sitesList;
    @Autowired
    private DeleteDataService deleteSite;
    @Autowired
    private WriteDbService writeSqlDbService;
    @Autowired
    private ChangeStartIndexingService changeStartIndexing;
    @Autowired
    private StopIndexingService stopIndexing;
    @Autowired
    private ParseService parseService;
    @Autowired
    private LemmaService lemmaService;
    @Autowired
    private DeleteCashLemmasService deleteCashLemmasService;


    //TODO есть баг со сбросом количества активных потоков т.е. при обрыве соединения вылетает исключение и повторно индексацию нельзя запустить. Исправить.

    /**
     * @see #startIndexing() - метод возвращает результат запуска индексации.
     * @see #indexing() - Метод запускает индексацию в параллельных потоках, количество потоков определяется количеством
     *      сайтов заданных в конфигурационном файле.
     */
    @Override
    public HashMap<String, Object> startIndexing() {
        HashMap<String, Object> response = new HashMap<>();
        if (changeStartIndexing.change()) {
            response.put("result", false);
            response.put("error", "Индексация уже запущена");
            return response;
        }
        indexing();
        response.put("result", true);
        return response;
    }

    /**
     *   В потоке выполняются следующие действия:
     * @see SiteDTO - создание объекта в нём содержится информация об объектах {@link SiteInfo} и {@link PageInfo};
     * @see WriteDbService#setStatus(String, Status, String) - сервис устанавиливает статус для сайтов
     *                              согласно переданным параметрам (URL, Status(INDEXED, INDEXING, FAILED), сообщение об ошибке);
     * @see DeleteCashLemmasService#delete(String) - сервис удаляет данные из БД REDIS по переданному Url;
     * @see DeleteDataService#delete(Site) - сервис удаляет данные из БД SQL;
     * @see WriteDbService#writeSiteTable(Site) - сервис подготавливает данные для запись в талицу {@link SiteInfo};
     * @see ParseService#getListPageDto(SiteDTO) - сервис предаёт параметры для запуска индексации и получает результат;
     * @see WriteDbService#writePageTable(SiteDTO) - сервис подготавливает данные для запись в талицу {@link PageInfo};
     * @see LemmaService#getListLemmas(int) - сервис получает список лемм и их общее количество на сайте;
     * @see WriteDbService#writeLemmaTable(SiteInfo, TreeMap) - сервис подготавливает данные для запись в талицу {@link Lemma};
     * @see WriteDbService#writeIndexTable(SiteInfo, TreeMap) - сервис подготавливает данные для запись в талицу {@link Index};
     * @see LifeThread#removeThread(Thread) - удаляет потоки из списка.
     */
    private void indexing() {
        for (Site site : sitesList.getSites()) {
            new Thread(() -> {
                addThread(Thread.currentThread());
                Thread.currentThread().setName(site.getName());
                try {
                    SiteDTO siteDTO = new SiteDTO();
                    writeSqlDbService.setStatus(site.getUrl(), Status.INDEXING, null);
                    deleteCashLemmasService.delete(site.getUrl());
                    deleteSite.delete(site);
                    writeSqlDbService.writeSiteTable(site);
                    siteDTO.setSiteInfo(writeSqlDbService.getSiteInfo(site));
                    parseService.getListPageDto(siteDTO);
                    writeSqlDbService.writePageTable(siteDTO);
                    TreeMap<Integer, List<LemmaDTO>> lemmas = lemmaService.getListLemmas(siteDTO.getSiteInfo().getId());
                    writeSqlDbService.writeLemmaTable(siteDTO.getSiteInfo(), lemmas);
                    writeSqlDbService.writeIndexTable(siteDTO.getSiteInfo(), lemmas);
                    writeSqlDbService.writeCash(siteDTO.getSiteInfo());
                    writeSqlDbService.setStatus(site.getUrl(), Status.INDEXED, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    writeSqlDbService.setStatus(site.getUrl(), Status.FAILED, ex.getMessage());
                }
                removeThread(Thread.currentThread());
            }).start();
        }
    }
}