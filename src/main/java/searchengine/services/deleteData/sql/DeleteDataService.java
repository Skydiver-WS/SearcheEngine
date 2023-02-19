package searchengine.services.deleteData.sql;

import searchengine.dto.sites.SiteDTO;

public interface DeleteDataService {
  void delete(SiteDTO siteDTO);
}
