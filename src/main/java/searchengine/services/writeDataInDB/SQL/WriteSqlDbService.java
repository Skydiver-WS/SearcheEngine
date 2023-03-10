package searchengine.services.writeDataInDB.SQL;

import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;

import java.util.List;

public interface WriteSqlDbService {
  void writeSiteTable(SiteDTO siteDTO);
  void writePageTable(SiteDTO siteDTO);
  void writeLemmaTable(List<LemmaDTO> lemmaDTOList);

}
