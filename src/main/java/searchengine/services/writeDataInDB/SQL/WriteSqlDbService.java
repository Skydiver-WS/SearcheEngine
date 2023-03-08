package searchengine.services.writeDataInDB.SQL;

import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.PageDTO;

import java.util.List;

public interface WriteSqlDbService {
  void writeSiteTable();
  void writePageTable(List<PageDTO> pageDTOList);
  void writeLemmaTable(List<LemmaDTO> lemmaDTOList);

}
