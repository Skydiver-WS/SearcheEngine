package searchengine.services.indexing.core.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.config.site.Site;
import searchengine.config.status.Status;
import searchengine.dto.sites.IndexDTO;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.sql.Index;
import searchengine.model.sql.Lemma;
import searchengine.model.sql.PageInfo;
import searchengine.model.sql.SiteInfo;
import searchengine.repository.sql.LemmaRepository;
import searchengine.repository.sql.PageRepository;
import searchengine.services.indexing.core.handler.lemmatable.HandlerDataLemmaService;
import searchengine.services.indexing.core.handler.indextable.HandlerDataIndexService;
import searchengine.services.writedatadb.sql.indextable.WriteIndexTableService;
import searchengine.services.writedatadb.sql.lemmatable.WriteLemmaTableService;
import searchengine.services.writedatadb.sql.pagetable.WritePageTableService;
import searchengine.services.writedatadb.sql.sitetable.WriteSiteTableService;
import searchengine.services.writedatadb.nossql.CashLemmasService;
import searchengine.services.writedatadb.nossql.CashStatisticsService;

import java.util.*;

/**
 * Данный класс является промежуточным  и подготавливает данные для запи в БД.
 * Включенный зависимости:
 *
 * @author Aleksandr Isaev
 * @see WriteSiteTableService - сервис осуществляет запись в таблицу {@link SiteInfo};
 * @see WritePageTableService - сервис осуществляет запись в таблицу {@link PageInfo};
 * @see WriteLemmaTableService - сервис осуществляет запись в таблицу {@link Lemma};
 * @see WriteIndexTableService - сервис осуществляет запись в таблицу {@link Index};
 * @see LemmaRepository - репозиторий для таблицы {@link Lemma};
 * @see PageRepository - репозиторий для таблицы {@link PageInfo};
 * @see HandlerDataIndexService - создание DTO объекта {@link IndexDTO} и передачи его в запись в таблицу {@link Lemma};
 * @see HandlerDataLemmaService - создание списка частоты лемм;
 * @see CashStatisticsService - сервис производит запись в БД Redis, для ускорения обновления статистики;
 * @see CashLemmasService - сервис производит запись в БД Redis, для данных увеличения скорости поиска результатов.
 */
@Component
public class WriteDbImpl implements WriteDbService {
    @Autowired
    private WriteSiteTableService writeSite;
    @Autowired
    private WritePageTableService writePage;
    @Autowired
    private WriteLemmaTableService writeLemmaTableService;
    @Autowired
    private WriteIndexTableService writeIndexTableService;
    @Autowired
    private LemmaRepository lemmaRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private HandlerDataIndexService handlerDataIndex;
    @Autowired
    private HandlerDataLemmaService handlerDataLemma;
    @Autowired
    private CashStatisticsService cashStatisticsService;
    @Autowired
    private CashLemmasService cashLemmasService;

    /**
     * @param site - информация о записываемом сайте;
     * @see #writeSiteTable(Site) - осуществляет запись в таблицу {@link SiteInfo}.
     * @see WriteSiteTableService#write(Site) - сервис передаёт данные для записи в таблицу {@link SiteInfo};
     * @see CashStatisticsService#setSiteStatistics(SiteInfo) - передаёт данные для записи в БД Redis в целях сбора статистики.
     */
    @Override
    public void writeSiteTable(Site site) {
        writeSite.write(site);
        cashStatisticsService.setSiteStatistics(getSiteInfo(site));
    }

    /**
     * @see #getSiteInfo(Site)  - получает объект {@link SiteInfo}
     */
    @Override
    public SiteInfo getSiteInfo(Site site) {
        return writeSite.getSiteInfo(site);
    }

    /**
     * @param url    - адресс сайта
     * @param status - статус выполнения индексации;
     * @param error  - сообщени о возможных ошибках.
     * @see #setStatus(String, Status, String) - метод устанавливает статус выполнения индексации;
     */
    @Override
    public void setStatus(String url, Status status, String error) {
        writeSite.setStatus(url, status, error);
    }

    /**
     * @see #writeSiteTable(Site) - метод осуществляет запись в таблицу {@link PageInfo}.
     */

    @Override
    public void writePageTable(SiteDTO siteDTO) {
        writePage.write(siteDTO);
    }

    /**
     * @see #writeLemmaTable(SiteInfo, TreeMap) - метод осуществляет запись в таблицу {@link Lemma}.
     * @see HandlerDataLemmaService#frequencyLemmas(TreeMap)
     */
    @Override
    public void writeLemmaTable(SiteInfo siteInfo, TreeMap<Integer, List<LemmaDTO>> lemmas) {
        Map<String, Integer> lemmasList = handlerDataLemma.frequencyLemmas(lemmas);
        writeLemmaTableService.write(siteInfo, lemmasList);
    }

    /**
     * @see #writeIndexTable(SiteInfo, TreeMap) - метод осуществляет запись в таблицу {@link Index}.
     * @see LemmaRepository#getLemmaTable(int) - получает список лемм из репозитория {@link LemmaRepository}
     * переданному id сайта из таблицы {@link SiteInfo};
     * @see PageRepository#getListPageTable(int) - получает список объектов из репозитория {@link PageRepository}
     * переданному id сайта из таблицы {@link SiteInfo};
     * @see HandlerDataIndexService#createIndexDTO(TreeMap, List, List) - создаёт DTO объект {@link IndexDTO} для передачи записи в таблицу {@link Index};
     * @see WriteIndexTableService#write(List) - сервис производит запись в таблицу {@link Index}.
     */
    @Override
    public void writeIndexTable(SiteInfo siteInfo, TreeMap<Integer, List<LemmaDTO>> lemmas) {
        List<Lemma> lemmaList = lemmaRepository.getLemmaTable(siteInfo.getId());
        List<PageInfo> pageList = pageRepository.getListPageTable(siteInfo.getId());
        List<IndexDTO> list = handlerDataIndex.createIndexDTO(lemmas, pageList, lemmaList);
        writeIndexTableService.write(list);
    }

    /**
     * @see #writeCash(SiteInfo) - метод производит запись в БД Redis по переданному параметру {@link SiteInfo}
     *                             или по id из таблицы {@link PageInfo}
     */

    @Override
    public void writeCash(SiteInfo siteInfo) {
        List<String> listLemmas = lemmaRepository.getLemmas(siteInfo.getId());
        cashLemmasService.writeLemmas(listLemmas);
    }

    @Override
    public void writeCash(int pageId) {
        cashLemmasService.writeLemmas(pageId);
    }

    @Override
    public void updateLemmaTable(SiteDTO siteDTO, TreeMap<Integer, List<LemmaDTO>> lemmas) {
        List<String> lemmasListNew = handlerDataLemma.frequencyLemmas(lemmas).keySet().stream().toList();
        List<Lemma> lemmaList = lemmaRepository.getLemmaTable(siteDTO.getSiteInfo().getId());
        List<Lemma> list = handlerDataLemma.checkNewLemmas(siteDTO.getSiteInfo(), lemmasListNew, lemmaList);
        writeLemmaTableService.updateLemmaTable(list);
    }
}
