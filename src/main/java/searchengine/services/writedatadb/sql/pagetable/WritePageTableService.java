package searchengine.services.writedatadb.sql.pagetable;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;

public interface WritePageTableService {
  void write(SiteDTO siteDTO);
  void updatePage(PageDTO pageDTO);
}