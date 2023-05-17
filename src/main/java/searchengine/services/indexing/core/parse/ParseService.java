package searchengine.services.indexing.core.parse;

import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;

public interface ParseService {
    void getListPageDto(SiteDTO siteDTO);
    PageDTO parsePage(PageDTO pageDTO);
}
