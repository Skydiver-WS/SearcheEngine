package searchengine.services.indexing.core.lemma;


import lombok.SneakyThrows;
import searchengine.dto.sites.LemmaDTO;

import java.util.List;
import java.util.TreeMap;

public interface LemmaService {
  @SneakyThrows
  TreeMap<Integer, List<LemmaDTO>> getListLemmas(int siteId);
}
