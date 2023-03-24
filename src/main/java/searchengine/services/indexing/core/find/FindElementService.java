package searchengine.services.indexing.core.find;

import searchengine.dto.sites.PageDTO;

public interface FindElementService {
    PageDTO find(String url);
}
