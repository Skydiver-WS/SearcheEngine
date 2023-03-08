package searchengine.services.deleteDataInDB.sql;

import searchengine.dto.sites.SiteDTO;

public interface DeleteDataService {
  void delete(SiteDTO siteDTO);
}
