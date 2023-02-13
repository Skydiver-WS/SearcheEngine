package searchengine.services.writeDB;

import searchengine.dto.sites.SiteDTO;

public interface WriteSiteDBService {
  void write(SiteDTO siteDTO);
  void setStatusIndexing(SiteDTO siteDTO);
}
