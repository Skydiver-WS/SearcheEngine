package searchengine.services.writeDataDB.SQL.pageTable;
import searchengine.dto.sites.SiteDTO;

public interface WritePageTableService {
  void write(SiteDTO siteDTO);
}