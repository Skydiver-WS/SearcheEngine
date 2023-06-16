package searchengine.services.writedatadb.sql.lemmatable;

import searchengine.model.sql.Lemma;
import searchengine.model.sql.SiteInfo;

import java.util.List;
import java.util.Map;

public interface WriteLemmaTableService {
    void write(SiteInfo siteInfo, Map<String, Integer> lemmas);

    void updateLemmaTable(List<Lemma> lemmaList);
}
