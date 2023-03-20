package searchengine.services.indexing.core.parse;

import searchengine.dto.sites.SiteDTO;

public interface ParseService {
    SiteDTO getListPageDto(SiteDTO siteDTO);
}
