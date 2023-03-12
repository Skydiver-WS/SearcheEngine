package searchengine.services.writeDataInDB.SQL.siteTable;

import searchengine.config.site.Site;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.SiteInfo;

public interface WriteSiteTableService {
  void write(Site site);
  SiteInfo getSiteInfo(Site site);
}
