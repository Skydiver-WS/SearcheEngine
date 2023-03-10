package searchengine.services.writeDataInDB.SQL.siteTable;

import searchengine.dto.sites.SiteDTO;

public interface WriteSiteTableService {
  void write(SiteDTO siteDTO);
  void setStatusIndexing();
}
