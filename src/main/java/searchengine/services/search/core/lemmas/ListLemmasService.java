package searchengine.services.search.core.lemmas;

import searchengine.dto.search.SearchObjectDTO;
import searchengine.model.noSQL.CashLemmas;

import java.util.List;

public interface ListLemmasService {
    List<SearchObjectDTO> getListObject(String[] lemmas);
}
