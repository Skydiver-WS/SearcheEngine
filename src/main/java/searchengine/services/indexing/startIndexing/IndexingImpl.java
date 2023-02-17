package searchengine.services.indexing.startIndexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.site.SitesList;
import searchengine.config.status.Status;
import searchengine.dto.sites.SiteDTO;
import searchengine.services.indexing.changeIndexing.ChangeStartIndexingService;
import searchengine.services.deleteData.sql.DeleteDataService;
import searchengine.services.statistics.packaging.CashStatisticsService;
import searchengine.services.writeDB.WritePageDBService;
import searchengine.services.writeDB.WriteSiteDBService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class IndexingImpl implements IndexingService {
    @Autowired
    private SitesList sitesList;
    @Autowired
    private WriteSiteDBService writeSite;
    @Autowired
    private WritePageDBService writePage;
    @Autowired
    private CashStatisticsService cashStatistics;
    @Autowired
    private DeleteDataService deleteSite;
    @Autowired
    private ChangeStartIndexingService changeStartIndexing;
    private static final ArrayList<Thread> threadList = new ArrayList<>();

    @Override
    public HashMap<String, Object> getIndexing() {
        HashMap<String, Object> response = new HashMap<>();
        if (changeStartIndexing.change(threadList)) {
            response.put("result", false);
            response.put("error", "Индексация уже запущена");
            return response;
        }
        threadList.clear();
        for (Site site : sitesList.getSites()) {
            Thread thread = new Thread(() -> {
                deleteSite.delete(site.getUrl());
                parse(site);
            });
            thread.setName(site.getName());
            threadList.add(thread);
            thread.start();
        }
        response.put("result", true);
        return response;
    }

    private void parse(Site site) {
        SiteDTO siteDTO = new SiteDTO();
        ParseHtmlPage parse = new ParseHtmlPage(site.getUrl());
        siteTableData(siteDTO, site);
        pageTableData(siteDTO, parse.invoke());
    }

    private void siteTableData(SiteDTO siteDTO, Site site) {
        siteDTO.setUrl(site.getUrl());
        siteDTO.setName(site.getName());
        siteDTO.setStatus(Status.INDEXING);
        siteDTO.setTime(LocalDateTime.now());
        siteDTO = writeSite.write(siteDTO);
        cashStatistics.setSiteStatistics(siteDTO);
    }

    private void pageTableData(SiteDTO siteDTO, Map<String, HashMap<Integer, String>> map) {
        siteDTO.setContent(map);
        siteDTO = writePage.write(siteDTO);
        siteDTO.setTime(LocalDateTime.now());
        siteDTO.setStatus(Status.INDEXED);
        siteDTO = writeSite.setStatusIndexing(siteDTO);
        cashStatistics.setPageStatistics(siteDTO);
    }
}