package searchengine.services.search;

import searchengine.dto.search.ResponseSearchDTO;

public interface SearchService {
     ResponseSearchDTO search(int offset, String query, String site,  int limit);
}
