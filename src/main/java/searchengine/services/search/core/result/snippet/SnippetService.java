package searchengine.services.search.core.result.snippet;

import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.model.sql.PageInfo;

import java.util.List;

public interface SnippetService {
    String getSnippet(PageInfo pageInfo, List<FrequencyLemmaDTO> list);
}
