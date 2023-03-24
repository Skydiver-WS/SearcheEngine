package searchengine.services.indexing.fullIndexing;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.site.SitesList;
import searchengine.config.status.Status;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.services.indexing.core.check.indexing.ChangeStartIndexingService;
import searchengine.services.deleteDataInDB.sql.DeleteDataService;
import searchengine.services.indexing.core.lemma.LemmaService;
import searchengine.services.indexing.core.parse.ParseService;
import searchengine.services.indexing.core.stopIndexing.StopIndexingImpl;
import searchengine.services.indexing.core.stopIndexing.StopIndexingService;
import searchengine.services.writeDataDB.SQL.WriteSqlDbService;

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
    private WriteSqlDbService writeSqlDbService;
    @Autowired
    private ChangeStartIndexingService changeStartIndexing;
    @Autowired
    private StopIndexingService stopIndexing;
    @Autowired
    private ParseService parseService;
    @Autowired
    private LemmaService lemmaService;

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

    @SneakyThrows
    private void indexing() {
        for (Site site : sitesList.getSites()) {
            new Thread(() -> {
                addThread(Thread.currentThread());
                Thread.currentThread().setName(site.getName());
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
                removeThread(Thread.currentThread());
            }).start();
        }
    }
}