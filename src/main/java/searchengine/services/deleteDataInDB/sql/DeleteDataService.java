package searchengine.services.deleteDataInDB.sql;

import searchengine.config.site.Site;

public interface DeleteDataService {
  void delete(Site site);
  void delete(int id);
}
