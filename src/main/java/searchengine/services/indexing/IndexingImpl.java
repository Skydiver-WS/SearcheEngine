package searchengine.services.indexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.site.SitesList;
import searchengine.config.status.Status;
import searchengine.dto.sites.SiteDTO;
import searchengine.services.deleteData.DeleteDataService;
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
    siteDTO.setStatus(Status.INDEXING);
    siteTableData(siteDTO, site);
    ParseHtmlPage parse = new ParseHtmlPage(site.getUrl());
    pageTableData(siteDTO, parse.invoke());
  }

  private void siteTableData(SiteDTO siteDTO, Site site) {
    siteDTO.setUrl(site.getUrl());
    siteDTO.setName(site.getName());
    siteDTO.setTime(LocalDateTime.now());
    writeSite.write(siteDTO);
  }

  private void pageTableData(SiteDTO siteDTO, Map<String, HashMap<Integer, String>> map) {
    siteDTO.setContent(map);
    writePage.write(siteDTO);
    siteDTO.setStatus(Status.INDEXED);
    writeSite.setStatusIndexing(siteDTO);
  }
}