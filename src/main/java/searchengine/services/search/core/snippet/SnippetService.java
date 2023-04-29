package searchengine.services.search.core.snippet;

import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.model.SQL.PageInfo;

import java.util.List;

public interface SnippetService {
    String getSnippet(PageInfo pageInfo, List<FrequencyLemmaDTO> list);
}
