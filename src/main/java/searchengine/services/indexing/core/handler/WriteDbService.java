package searchengine.services.indexing.core.handler;

import searchengine.config.site.Site;
import searchengine.config.status.Status;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.sql.SiteInfo;

import java.util.List;
import java.util.TreeMap;

public interface WriteDbService {
  void writeSiteTable(Site site);
  SiteInfo getSiteInfo(Site site);
  void setStatus(String url, Status status, String error);
  void writePageTable(SiteDTO siteDTO);
  void writeLemmaTable(SiteInfo siteInfo, TreeMap<Integer, List<LemmaDTO>> lemmas);
  void updateLemmaTable(SiteDTO siteDTO, TreeMap<Integer, List<LemmaDTO>> lemmas);
  void writeIndexTable(SiteInfo siteInfo, TreeMap<Integer, List<LemmaDTO>> lemmas);
  void writeCash(SiteInfo siteInfo);
  void writeCash(int pageId);

}
