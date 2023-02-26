package searchengine.services.writeDB.SQL;
import searchengine.dto.sites.SiteDTO;

public interface WritePageTableService {
  SiteDTO write(SiteDTO siteDTO);
}