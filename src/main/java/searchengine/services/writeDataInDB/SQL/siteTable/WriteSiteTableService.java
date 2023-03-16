package searchengine.services.writeDataInDB.SQL.siteTable;

import searchengine.config.site.Site;
import searchengine.config.status.Status;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.SiteInfo;

public interface WriteSiteTableService {
  void write(Site site);
  SiteInfo getSiteInfo(Site site);

  void setStatus(String url, Status status, String error);
}
