package searchengine.services.indexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.config.Status;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.PageInfo;
import searchengine.model.SiteInfo;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.writeDB.WriteSiteDBService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class IndexingImpl implements IndexingService {
  @Autowired
  private PageRepository pageRepository;
  @Autowired
  private SiteRepository siteRepository;
  @Autowired
  private SitesList sitesList;
  @Autowired
  private WriteSiteDBService writeSite;
  @Autowired
  private SiteDTO siteDTO;

  @Override
  public boolean getIndexing() {
    for (Site site : sites()) {
      String url = site.getUrl();
      deleteSite(url);
      new Thread(() -> {
        siteDTO.setUrl(site.getUrl());
        siteDTO.setName(site.getName());
        siteDTO.setStatus(Status.INDEXING);
        siteDTO.setTime(LocalDateTime.now());
        writeSite.write(siteDTO);
        parse(url);
      }).start();
    }
    return false;
  }

  private void deleteSite(String site) {
    for (SiteInfo siteInfo : sitesInfo()) {
      if (siteInfo.getUrl().equals(site)) {
        //deletePages(siteInfo.getId()); //TODO: вернуть, как пропишу удаление PAGE
        siteRepository.deleteById(siteInfo.getId());
      }
    }
  }

  private void deletePages(int siteId) {
    for (PageInfo pageInfo : pageInfo()) {
      if (pageInfo.getSiteId().getId() == siteId) {
        pageRepository.deleteById(pageInfo.getId());
      }
    }
    pageRepository.deleteById(siteId);
  }


  private void parse(String url) {
    ParseHtmlPage parse = new ParseHtmlPage(url);
  }

  private List<Site> sites() {
    return sitesList.getSites();
  }

  private List<SiteInfo> sitesInfo() {
    return siteRepository.findAll();
  }

  private List<PageInfo> pageInfo() {
    return pageRepository.findAll();
  }
}