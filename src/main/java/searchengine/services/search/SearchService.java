package searchengine.services.search;

import searchengine.dto.search.ResponseSearchDTO;

public interface SearchService {
     ResponseSearchDTO search(String query, String site, int offset, int limit);
}
