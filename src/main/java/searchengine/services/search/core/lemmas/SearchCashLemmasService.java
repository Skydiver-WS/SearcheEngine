package searchengine.services.search.core.lemmas;

import searchengine.dto.search.SearchObjectDTO;

import java.util.List;

public interface SearchCashLemmasService {
    List<SearchObjectDTO> listLemmas(String lemma);
}
