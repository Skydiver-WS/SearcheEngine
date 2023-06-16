package searchengine.services.indexing.core.handler.lemmatable;

import searchengine.dto.sites.LemmaDTO;
import searchengine.model.sql.Lemma;
import searchengine.model.sql.SiteInfo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface HandlerDataLemmaService {
    Map<String, Integer> frequencyLemmas(TreeMap<Integer, List<LemmaDTO>> listLemmas);
    List<Lemma> checkNewLemmas(SiteInfo siteInfo, List<String> lemmasListNew, List<Lemma> lemmaList);
}
