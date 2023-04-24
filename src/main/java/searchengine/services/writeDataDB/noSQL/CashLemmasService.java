package searchengine.services.writeDataDB.noSQL;

import searchengine.model.SQL.Lemma;

import java.util.List;

public interface CashLemmasService {
    void writeLemmas(List<Lemma> lemmaList);
}
