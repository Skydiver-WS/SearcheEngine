package searchengine.services.search.core.relative;

import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.dto.search.RelevanceDTO;

import java.util.List;

public interface DefiniteRelevanceService {
    RelevanceDTO[] getList(List<FrequencyLemmaDTO> list);
}
