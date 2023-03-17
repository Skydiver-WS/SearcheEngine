package searchengine.services.writeDataInDB.SQL.siteTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.status.Status;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.SiteRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WriteSiteTableImpl implements WriteSiteTableService {
  @Autowired
  private SiteRepository siteRepository;


  @Override
  public void write(Site site) {
    Optional<SiteInfo> optional = siteRepository.getSiteInfo(site.getUrl());
    if (optional.isEmpty()){
      SiteInfo siteInfo = new SiteInfo();
      siteInfo.setStatus(Status.INDEXING);
      siteInfo.setStatusTime(LocalDateTime.now());
      siteInfo.setName(site.getName());
      siteInfo.setUrl(site.getUrl());
      synchronized (siteRepository){
        siteRepository.save(siteInfo);
      }
    }
  }

  @Override
  public SiteInfo getSiteInfo(Site site) {
    return siteRepository.getSiteInfo(site.getUrl()).get();
  }

  @Override
  public void setStatus(String url, Status status, String error) {
    Optional<SiteInfo> siteInfo = siteRepository.getSiteInfo(url);
    siteInfo.ifPresent(info -> siteRepository.updateStatus(info.getId(), LocalDateTime.now(), status.toString(), error));
  }
}
