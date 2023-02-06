package searchengine.services.writeDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SiteInfo;
import searchengine.repository.SiteRepository;

@Service
public class WriteSiteTable implements WriteSiteDBService {
  @Autowired
  private SiteRepository siteRepository;
  @Autowired
  SiteInfo siteInfo;

  @Override
  public synchronized void write(SiteDTO siteDTO) {
    int id = 0;
    siteInfo.setId(1);
    siteInfo.setUrl(siteDTO.getUrl());
    siteInfo.setName(siteDTO.getName());
    siteInfo.setStatus(siteDTO.getStatus());
    siteInfo.setStatusTime(siteDTO.getTime());
    siteRepository.save(siteInfo);
  }
}
