package searchengine.services.writeDB;

import searchengine.dto.sites.SiteDTO;

import java.util.Set;

public interface WriteSiteDBService {
  void write(SiteDTO siteDTO);
}
