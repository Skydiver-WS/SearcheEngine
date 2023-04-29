package searchengine.services.search;

import searchengine.dto.search.ResponseSearch;

import java.util.HashMap;

public interface SearchService {
     ResponseSearch search(String query, String site, int offset, int limit);
}
