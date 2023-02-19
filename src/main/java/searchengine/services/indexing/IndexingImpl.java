package searchengine.services.indexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.site.SitesList;
import searchengine.config.status.Status;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.indexing.changeIndexing.ChangeStartIndexingService;
import searchengine.services.deleteData.sql.DeleteDataService;
import searchengine.services.indexing.parse.ParseHtmlPage;
import searchengine.services.indexing.stopIndexing.StopIndexingService;
import searchengine.services.writeDB.noSQL.CashStatisticsService;
import searchengine.services.writeDB.SQL.WritePageDBService;
import searchengine.services.writeDB.SQL.WriteSiteDBService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @Autowired
    private StopIndexingService stopIndexing;
    private static final ArrayList<Thread> threadList = new ArrayList<>();

    @Override
    public HashMap<String, Object> startIndexing() {
        HashMap<String, Object> response = new HashMap<>();
        if (changeStartIndexing.change(threadList)) {
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
        if(stopIndexing.stop(threadList)){
            response.put("result", true);
            threadList.clear();
            return  response;
        }
        response.put("result", false);
        response.put("error", "Индексация не запущена");
        return null;
    }

    private void indexing(){
        for (Site site : sitesList.getSites()) {
            Thread thread = new Thread(() -> {
                SiteDTO siteDTO = new SiteDTO();
                siteDTO.setName(site.getName());
                siteDTO.setUrl(site.getUrl());
                siteDTO.setStatus(Status.INDEXING);
                siteDTO.setError("");
                deleteSite.delete(siteDTO);
                parse(siteDTO);
            });
            thread.setName(site.getName());
            threadList.add(thread);//TODO не большой баг. При удалении со всех таблиц, но пока не запустилась индексация можно запустить повторно
            thread.start();
        }
    }

    private void parse(SiteDTO siteDTO) {
        ParseHtmlPage parse = new ParseHtmlPage(siteDTO.getUrl());
        writeSiteTableData(siteDTO);
        writePageTableData(siteDTO);
        siteDTO.setContent(parse.invoke());
    }

    private void writeSiteTableData(SiteDTO siteDTO) {
        siteDTO.setTime(LocalDateTime.now());
        siteDTO = writeSite.write(siteDTO);
        cashStatistics.setSiteStatistics(siteDTO);
    }

    private void writePageTableData(SiteDTO siteDTO) {
        siteDTO = writePage.write(siteDTO);
        siteDTO.setTime(LocalDateTime.now());
        siteDTO.setStatus(Status.INDEXED);
        siteDTO = writeSite.setStatusIndexing(siteDTO);
        cashStatistics.setPageStatistics(siteDTO);
    }

}