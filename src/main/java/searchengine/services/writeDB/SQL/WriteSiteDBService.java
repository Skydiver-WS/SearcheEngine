package searchengine.services.writeDB.SQL;

import searchengine.dto.sites.SiteDTO;

public interface WriteSiteDBService {
  SiteDTO write(SiteDTO siteDTO);
  SiteDTO setStatusIndexing(SiteDTO siteDTO);
}
