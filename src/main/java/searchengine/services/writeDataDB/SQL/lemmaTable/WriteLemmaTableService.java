package searchengine.services.writeDataDB.SQL.lemmaTable;

import searchengine.model.SQL.SiteInfo;

import java.util.Map;

public interface WriteLemmaTableService {
    void write(SiteInfo siteInfo, Map<String, Integer> lemmas);
}
