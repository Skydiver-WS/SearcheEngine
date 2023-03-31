package searchengine.services.writeDataDB.SQL.lemmaTable;

import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.SiteInfo;

import java.util.List;
import java.util.Map;

public interface WriteLemmaTableService {
    void write(SiteInfo siteInfo, Map<String, Integer> lemmas);

    void updateLemmaTable(List<Lemma> lemmaList);
}
