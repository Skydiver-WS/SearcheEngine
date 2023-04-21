package searchengine.services.search.core.lemmas;

import searchengine.dto.search.FrequencyLemmaDTO;

public interface SearchLemmasService {
    FrequencyLemmaDTO[] getFindLemmasSort(String query);
}
