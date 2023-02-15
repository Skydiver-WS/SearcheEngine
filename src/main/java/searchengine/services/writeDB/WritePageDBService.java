package searchengine.services.writeDB;
import searchengine.dto.sites.SiteDTO;

public interface WritePageDBService {
  SiteDTO write(SiteDTO siteDTO);
}