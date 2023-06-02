package searchengine.services.indexing.singleIndexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.site.SitesList;
import searchengine.config.status.Status;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.Index;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;
import searchengine.model.SQL.SiteInfo;
import searchengine.services.deleteDataDB.nosql.DeleteCashLemmasService;
import searchengine.services.deleteDataDB.sql.DeleteDataService;
import searchengine.services.indexing.core.check.indexing.ChangeStartIndexingService;
import searchengine.services.indexing.core.check.lifeThread.LifeThread;
import searchengine.services.indexing.core.check.url.CheckUrlService;
import searchengine.services.indexing.core.find.FindElementService;
import searchengine.services.indexing.core.lemma.LemmaService;
import searchengine.services.indexing.core.parse.ParseService;
import searchengine.services.indexing.core.handler.WriteDbService;
import searchengine.services.stopIndexing.StopIndexingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Данный класс запускает индексацию страницы, удаление и запись в БД MySQL и REDIS.
 * Включённые зависимости:
 * @see CheckUrlService - сервис проверяет входит ли передаваемый запрос в конфигурационном файле;
 * @see FindElementService - сервис осуществляет поиск в таблице {@link PageInfo}
 * @see ParseService - сервис для получение html кода страницы;
 * @see WriteDbService - сервис запускает запись в базу данных. Промежуточный класс для записи и изменения данных, а так же передачи
 *                       готовых объектов для записи в БД;
 * @see LemmaService - сервис запускает приведение слов к их исходной форме;
 * @see DeleteDataService - сервис запускает удаление из БД;
 * @see DeleteCashLemmasService - сервис удаляет данные из БД Redis.
 * @author Aleksandr Isaev
 */

@Service
public class IndexPageImpl implements IndexPageService {
    @Autowired
    private CheckUrlService checkUrl;
    @Autowired
    private FindElementService findElementService;
    @Autowired
    private ParseService parseService;
    @Autowired
    private WriteDbService writeSqlDbService;
    @Autowired
    private LemmaService lemmaService;
    @Autowired
    private DeleteDataService deleteDataService;
    @Autowired
    private DeleteCashLemmasService deleteCashLemmasService;
    private final SiteDTO siteDTO = new SiteDTO();

    /**
     *  Метод выполняет индексацию страницы.
     * @see CheckUrlService#check(String) - метод проверяет входит ли передаваемый запрос в конфигурационном файле;
     * @see FindElementService#find(String) - метод выполняет поиск заданной страницы и возвращает объект {@link PageDTO};
     * @see WriteDbService#setStatus(String, Status, String) - сервис устанавиливает статус для сайтов
     *                              согласно переданным параметрам (URL, Status(INDEXED, INDEXING, FAILED), сообщение об ошибке);
     * @see ParseService#parsePage(PageDTO) - метод получает html код страницы и возвращает объект {@link PageDTO};
     * @see DeleteCashLemmasService#delete(String) - сервис удаляет данные из БД REDIS по переданному Url;
     * @see DeleteDataService#delete(Site) - сервис удаляет данные из БД SQL;
     * @see ParseService#getListPageDto(SiteDTO) - сервис предаёт параметры для запуска индексации и получает результат;
     * @see WriteDbService#writePageTable(SiteDTO) - сервис подготавливает данные для запись в талицу {@link PageInfo};
     * @see LemmaService#getListLemmas(int) - сервис получает список лемм и их общее количество на сайте;
     * @see WriteDbService#writeLemmaTable(SiteInfo, TreeMap) - сервис подготавливает данные для запись в талицу {@link Lemma};
     * @see WriteDbService#writeIndexTable(SiteInfo, TreeMap) - сервис подготавливает данные для запись в талицу {@link Index};
     */
    @Override
    public HashMap<String, Object> indexPage(String url) {
        HashMap<String, Object> response = new HashMap<>();
        if (checkUrl.check(url)) {
            new Thread(()->{
                List<PageDTO> list = new ArrayList<>();
                PageDTO pageDTO = findElementService.find(url);
                siteDTO.setSiteInfo(pageDTO.getSiteInfo());
                writeSqlDbService.setStatus(siteDTO.getSiteInfo().getUrl(), Status.INDEXING, null);
                list.add(pageDTO);
                pageDTO = parseService.parsePage(pageDTO);
                siteDTO.setPageDTOList(list);
                deleteCashLemmasService.delete(pageDTO.getId());
                deleteDataService.delete(siteDTO);
                writeSqlDbService.writePageTable(siteDTO);
                TreeMap<Integer, List<LemmaDTO>> lemmas = lemmaService.getListLemmas(siteDTO.getPageDTOList());
                writeSqlDbService.updateLemmaTable(siteDTO, lemmas);
                writeSqlDbService.writeIndexTable(siteDTO.getSiteInfo(), lemmas);
                writeSqlDbService.writeCash(pageDTO.getId());
                writeSqlDbService.setStatus(siteDTO.getSiteInfo().getUrl(), Status.INDEXED, null);
            }).start();
            response.put("result", true);
        } else {
            response.put("result", false);
            response.put("error", "Данная страница находится за пределами сайтов, " +
                    "указанных в конфигурационном файле.");
        }
        return response;
    }
}
