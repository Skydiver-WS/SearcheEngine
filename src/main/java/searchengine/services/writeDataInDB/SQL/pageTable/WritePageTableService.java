package searchengine.services.writeDataInDB.SQL.pageTable;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;

import java.util.List;

public interface WritePageTableService {
  void write(SiteDTO siteDTO);
}