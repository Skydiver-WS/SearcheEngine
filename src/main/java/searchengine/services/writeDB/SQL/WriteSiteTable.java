package searchengine.services.writeDB.SQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.SiteRepository;

import java.util.Optional;

@Service
public class WriteSiteTable implements WriteSiteDBService {
  @Autowired
  private SiteRepository siteRepository;
  @Autowired
  private SiteInfo siteInfo;


  @Override
  public synchronized SiteDTO write(SiteDTO siteDTO) {
    siteInfo.setId(0);
    siteInfo.setUrl(siteDTO.getUrl());
    siteInfo.setName(siteDTO.getName());
    siteInfo.setStatus(siteDTO.getStatus());
    siteInfo.setStatusTime(siteDTO.getTime());
    siteRepository.saveAndFlush(siteInfo);
    getId(siteDTO);
    return siteDTO;
  }

  @Override
  public synchronized SiteDTO setStatusIndexing(SiteDTO siteDTO) {
    Optional<SiteInfo> siteById = siteRepository.findById(siteDTO.getIdSite());
    if (siteById.isPresent()){
      siteInfo = siteById.get();
      siteInfo.setStatusTime(siteDTO.getTime());
      siteInfo.setStatus(siteDTO.getStatus());
      siteInfo.setLastError(siteDTO.getError());
      siteRepository.saveAndFlush(siteInfo);
    }
    return siteDTO;
  }

  private void getId(SiteDTO siteDTO) {
    for (SiteInfo site : siteRepository.findAll()) {
      if (site.getUrl().contains(siteDTO.getUrl())) {
        siteDTO.setIdSite(site.getId());
      }
    }
  }
}
