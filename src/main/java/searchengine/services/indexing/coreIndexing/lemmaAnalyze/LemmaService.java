package searchengine.services.indexing.coreIndexing.lemmaAnalyze;


import lombok.SneakyThrows;
import searchengine.dto.sites.LemmaDTO;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public interface LemmaService {
  @SneakyThrows
  TreeMap<Integer, List<LemmaDTO>> getListLemmas(int siteId);
}
