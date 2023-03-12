package searchengine.services.writeDataInDB.SQL.lemmaTable;

import searchengine.dto.sites.LemmaDTO;
import searchengine.model.SQL.SiteInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WriteLemmaTableService {
    void write(SiteInfo siteInfo, Map<String, Integer> lemmas);
}
