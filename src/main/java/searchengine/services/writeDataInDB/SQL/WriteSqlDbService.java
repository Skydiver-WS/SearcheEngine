package searchengine.services.writeDataInDB.SQL;

import searchengine.config.site.Site;
import searchengine.config.status.Status;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.SiteInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public interface WriteSqlDbService {
  void writeSiteTable(Site site);
  SiteInfo getSiteInfo(Site site);
  void setStatus(String url, Status status, String error);
  void writePageTable(SiteDTO siteDTO);
  void writeLemmaTable(SiteInfo siteInfo, TreeMap<Integer, List<LemmaDTO>> lemmas);
  void writeIndexTable(SiteInfo siteInfo, TreeMap<Integer, List<LemmaDTO>> lemmas);

}
