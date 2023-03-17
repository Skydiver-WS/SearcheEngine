package searchengine.services.indexing.coreIndexing.parse;

import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;

import java.util.List;

public interface ParseService {
    SiteDTO getListPageDto(SiteDTO siteDTO);
}
