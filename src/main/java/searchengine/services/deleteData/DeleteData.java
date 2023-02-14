package searchengine.services.deleteData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.model.PageInfo;
import searchengine.model.SiteInfo;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.util.List;

@Service
public class DeleteData implements DeleteDataService {
  @Autowired
  private PageRepository pageRepository;
  @Autowired
  private SiteRepository siteRepository;

  @Override
  public void delete(String url) {
    deleteSite(url);
  }

  private void deleteSite(String site) {
    for (SiteInfo siteInfo : getSitesInfo()) {
      if (siteInfo.getUrl().equals(site)) {
        deletePages(siteInfo.getId());
        siteRepository.deleteById(siteInfo.getId());
      }
    }
  }

  private void deletePages(int siteId) {
    for (PageInfo pageInfo : getPageInfo()) {
      if (pageInfo.getSiteId().getId() == siteId) {
        pageRepository.deleteById(pageInfo.getId());
      }
    }
  }

  private synchronized List<SiteInfo> getSitesInfo() {
    return siteRepository.findAll();
  }

  private synchronized List<PageInfo> getPageInfo() {
    return pageRepository.findAll();
  }
}
