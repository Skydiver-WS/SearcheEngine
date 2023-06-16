package searchengine.services.writedatadb.sql.sitetable;

import searchengine.config.site.Site;
import searchengine.config.status.Status;
import searchengine.model.sql.SiteInfo;

public interface WriteSiteTableService {
  void write(Site site);
  SiteInfo getSiteInfo(Site site);

  void setStatus(String url, Status status, String error);
}
