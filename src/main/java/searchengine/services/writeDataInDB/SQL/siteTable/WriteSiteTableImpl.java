package searchengine.services.writeDataInDB.SQL.siteTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.status.Status;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.SiteRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WriteSiteTableImpl implements WriteSiteTableService {
  @Autowired
  private SiteRepository siteRepository;


  @Override
  public void write(SiteDTO siteDTO) {
    Optional<SiteInfo> siteInfo = siteRepository.findById(siteDTO.getId());
    if(siteInfo.isEmpty()){
      SiteInfo site = new SiteInfo();
      site.setId(0);
      site.setStatus(Status.INDEXING);
      site.setStatusTime(LocalDateTime.now());
      site.setUrl(siteDTO.getUrl());
      site.setName(siteDTO.getName());
      siteRepository.save(site);
    }
  }

  @Override
  public void setStatusIndexing() {

  }
}
