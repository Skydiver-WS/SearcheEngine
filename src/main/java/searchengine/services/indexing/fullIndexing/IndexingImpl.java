package searchengine.services.indexing.fullIndexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.site.SitesList;
import searchengine.config.status.Status;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.repository.noSQL.CashLemmasRepository;
import searchengine.services.deleteDataDB.nosql.DeleteCashLemmasService;
import searchengine.services.indexing.core.check.indexing.ChangeStartIndexingService;
import searchengine.services.deleteDataDB.sql.DeleteDataService;
import searchengine.services.indexing.core.lemma.LemmaService;
import searchengine.services.indexing.core.parse.ParseService;
import searchengine.services.stopIndexing.StopIndexingService;
import searchengine.services.indexing.core.handler.WriteDbService;

import java.util.*;

import static searchengine.services.indexing.core.check.lifeThread.LifeThread.addThread;
import static searchengine.services.indexing.core.check.lifeThread.LifeThread.removeThread;

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
    @Override
    public HashMap<String, Object> startIndexing() {
        HashMap<String, Object> response = new HashMap<>();
        deleteCashLemmasService.delete();
        if (changeStartIndexing.change()) {
            response.put("result", false);
            response.put("error", "Индексация уже запущена");
            return response;
        }
        indexing();
        response.put("result", true);
        return response;
    }

    private void indexing() {
        for (Site site : sitesList.getSites()) {
            new Thread(() -> {
                addThread(Thread.currentThread());
                Thread.currentThread().setName(site.getName());
                try {
                    SiteDTO siteDTO = new SiteDTO();
                    writeSqlDbService.setStatus(site.getUrl(), Status.INDEXING, null);
                    deleteSite.delete(site);
                    writeSqlDbService.writeSiteTable(site);
                    siteDTO.setSiteInfo(writeSqlDbService.getSiteInfo(site));
                    parseService.getListPageDto(siteDTO);
                    writeSqlDbService.writePageTable(siteDTO);
                    TreeMap<Integer, List<LemmaDTO>> lemmas = lemmaService.getListLemmas(siteDTO.getSiteInfo().getId());
                    writeSqlDbService.writeLemmaTable(siteDTO.getSiteInfo(), lemmas);
                    writeSqlDbService.writeIndexTable(siteDTO.getSiteInfo(), lemmas);
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