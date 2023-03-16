package searchengine.services.indexing;

import lombok.SneakyThrows;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.site.SitesList;
import searchengine.config.status.Status;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.services.indexing.checkIndexing.ChangeStartIndexingService;
import searchengine.services.deleteDataInDB.sql.DeleteDataService;
import searchengine.services.indexing.lemmaAnalyze.LemmaService;
import searchengine.services.indexing.parse.ParseService;
import searchengine.services.indexing.stopIndexing.StopIndexingService;
import searchengine.services.writeDataInDB.SQL.WriteSqlDbService;

import java.util.*;
import java.util.logging.Logger;

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
    private final List<Thread> threadList = Collections.synchronizedList(new ArrayList<>());


    @Override
    public HashMap<String, Object> startIndexing() {
        HashMap<String, Object> response = new HashMap<>();
        if (changeStartIndexing.change()) {
            response.put("result", false);
            response.put("error", "Индексация уже запущена");
            return response;
        }
        threadList.clear();
        indexing();
        response.put("result", true);
        return response;
    }

    @Override
    public HashMap<String, Object> stopIndexing() {
        HashMap<String, Object> response = new HashMap<>();
        if (stopIndexing.check()) {
            for (Thread thread : threadList) {
                thread.interrupt();
            }
            for (Thread thread : threadList) {
                try {


                    thread.join();
                    Logger.getLogger(IndexingImpl.class.getName()).info(thread.getName() + " stop");
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            response.put("result", true);
            threadList.clear();
            return response;
        }
        response.put("result", false);
        response.put("error", "Индексация не запущена");
        return response;
    }

    @SneakyThrows
    private void indexing() {
        for (Site site : sitesList.getSites()) {
            new Thread(() -> {
                Thread.currentThread().setName(site.getName());
                threadList.add(Thread.currentThread());
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
                threadList.remove(Thread.currentThread());
                writeSqlDbService.setStatus(site.getUrl(), Status.INDEXED, null);
            }).start();
        }
    }

    public boolean isAliveThread() {
        return threadList.size() > 0;
    }

}