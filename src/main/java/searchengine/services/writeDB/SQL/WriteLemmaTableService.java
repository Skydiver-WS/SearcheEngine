package searchengine.services.writeDB.SQL;

import searchengine.dto.sites.SiteDTO;

public interface WriteLemmaTableService {
    void write(SiteDTO siteDTO);
}
