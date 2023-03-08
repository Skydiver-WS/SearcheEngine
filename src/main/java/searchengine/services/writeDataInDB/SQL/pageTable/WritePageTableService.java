package searchengine.services.writeDataInDB.SQL.pageTable;
import searchengine.dto.sites.PageDTO;

import java.util.List;

public interface WritePageTableService {
  void write(List<PageDTO> pageDTOList);
}