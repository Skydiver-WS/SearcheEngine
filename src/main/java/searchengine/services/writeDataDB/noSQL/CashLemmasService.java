package searchengine.services.writeDataDB.noSQL;

import searchengine.dto.search.SearchObjectDTO;
import searchengine.model.SQL.Lemma;

import java.util.List;

public interface CashLemmasService {
    void writeLemmas(List<String> listLemmas);
    void writeLemmas(int pageId);
}
