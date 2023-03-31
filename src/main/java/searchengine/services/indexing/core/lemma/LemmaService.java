package searchengine.services.indexing.core.lemma;


import lombok.SneakyThrows;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.PageDTO;

import java.util.List;
import java.util.TreeMap;

public interface LemmaService {
  TreeMap<Integer, List<LemmaDTO>> getListLemmas(int siteId);
  TreeMap<Integer, List<LemmaDTO>> getListLemmas(List<PageDTO> list);
}
