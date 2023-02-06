package searchengine.services.writeDB;
import searchengine.dto.sites.SiteDTO;

public interface WritePageDBService {
  void write(SiteDTO siteDTO);
}