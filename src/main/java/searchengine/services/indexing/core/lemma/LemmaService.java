package searchengine.services.indexing.core.lemma;


import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.PageDTO;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface LemmaService {
  TreeMap<Integer, List<LemmaDTO>> getListLemmas(int siteId);
  TreeMap<Integer, List<LemmaDTO>> getListLemmas(List<PageDTO> list);
  Map<String, Integer> getListLemmas (String query);
}
