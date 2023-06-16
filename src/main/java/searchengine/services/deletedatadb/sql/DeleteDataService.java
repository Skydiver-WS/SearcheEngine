package searchengine.services.deletedatadb.sql;

import searchengine.config.site.Site;
import searchengine.dto.sites.SiteDTO;

public interface DeleteDataService {
  void delete(Site site);
  void delete(SiteDTO siteDTO);
}
