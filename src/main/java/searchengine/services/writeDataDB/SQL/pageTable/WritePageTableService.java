package searchengine.services.writeDataDB.SQL.pageTable;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;

public interface WritePageTableService {
  void write(SiteDTO siteDTO);
  void update(PageDTO pageDTO);
}