package searchengine.services.writeDB.SQL;
import searchengine.dto.sites.SiteDTO;

public interface WritePageDBService {
  SiteDTO write(SiteDTO siteDTO);
}