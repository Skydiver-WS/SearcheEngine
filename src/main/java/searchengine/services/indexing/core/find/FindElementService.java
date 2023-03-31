package searchengine.services.indexing.core.find;

import searchengine.dto.sites.PageDTO;

import java.util.List;

public interface FindElementService {
    PageDTO find(String url);
}
