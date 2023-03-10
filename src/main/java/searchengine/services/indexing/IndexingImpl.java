package searchengine.services.indexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.site.SitesList;
import searchengine.dto.sites.SiteDTO;
import searchengine.services.indexing.checkIndexing.ChangeStartIndexingService;
import searchengine.services.deleteDataInDB.sql.DeleteDataService;
import searchengine.services.indexing.parse.ParseService;
import searchengine.services.indexing.stopIndexing.StopIndexingService;
import searchengine.services.writeDataInDB.SQL.WriteSqlDbService;

import java.util.ArrayList;
import java.util.HashMap;

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
  private static final ArrayList<Thread> threadList = new ArrayList<>();
  private final SiteDTO siteDTO = new SiteDTO();

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
    if (stopIndexing.stop(threadList)) {
      Thread.currentThread().interrupt();
      response.put("result", true);
      threadList.clear();
      return response;
    }
    response.put("result", false);
    response.put("error", "Индексация не запущена");
    return response;
  }

  private void indexing() {
    for (Site site : sitesList.getSites()) {
      new Thread(() -> {
        threadList.add(Thread.currentThread());
        siteDTO.setUrl(site.getUrl());
        siteDTO.setName(site.getName());
        deleteSite.delete(siteDTO);
        writeSqlDbService.writeSiteTable(siteDTO);
        parseService.getListPageDto(siteDTO);
        writeSqlDbService.writePageTable(siteDTO);
        threadList.remove(Thread.currentThread());
      }).start();
    }
  }


  public static boolean isAliveThread() {
    return threadList.size() > 0;
  }
}