package searchengine.services.writeDataInDB.SQL.indexTable;

import searchengine.dto.sites.LemmaDTO;
import searchengine.model.SQL.SiteInfo;

import java.util.List;
import java.util.Set;

public interface WriteIndexTableService {
  void write(SiteInfo siteInfo, Set<List<LemmaDTO>> lemmas);
}
