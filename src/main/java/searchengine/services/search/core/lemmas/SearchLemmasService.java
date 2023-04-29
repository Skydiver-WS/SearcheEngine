package searchengine.services.search.core.lemmas;

import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.dto.search.SearchObjectDTO;

import java.util.List;

public interface SearchLemmasService {
    FrequencyLemmaDTO[] getFindLemmasSort(String [] lemmas, List<SearchObjectDTO> searchObjectDTOList);
}
