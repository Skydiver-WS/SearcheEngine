package searchengine.services.indexing.core.handler.indextable;

import searchengine.dto.sites.IndexDTO;
import searchengine.dto.sites.LemmaDTO;
import searchengine.model.sql.Lemma;
import searchengine.model.sql.PageInfo;

import java.util.List;
import java.util.TreeMap;

public interface HandlerDataIndexService {
    List<IndexDTO> createIndexDTO(TreeMap<Integer, List<LemmaDTO>> lemmas,
                                  List<PageInfo> pageList,
                                  List<Lemma> lemmaList);
}
