package searchengine.services.indexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.site.SitesList;
import searchengine.config.status.Status;
import searchengine.dto.sites.SiteDTO;
import searchengine.services.deleteData.DeleteDataService;
import searchengine.services.statistics.redis.CashStatisticsService;
import searchengine.services.writeDB.WritePageDBService;
import searchengine.services.writeDB.WriteSiteDBService;

import java.time.LocalDateTime;
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

  @Override
  public boolean getIndexing() {
    for (Site site : sitesList.getSites()) {
      new Thread(() -> {
        deleteSite.delete(site.getUrl());
        parse(site);
      }).start();
    }
    return false;
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