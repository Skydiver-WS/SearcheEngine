package searchengine.services.writeDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.PageInfo;
import searchengine.model.SiteInfo;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
@Service
public class WritePageTable implements WritePageDBService {
  @Autowired
  private SiteRepository siteRepository;
  @Autowired
  private PageRepository pageRepository;
  @Autowired
  private PageInfo pageInfo;
  @Autowired
  private SiteInfo siteInfo;
  @Override
  public void write(SiteDTO siteDTO) {

  }

}
