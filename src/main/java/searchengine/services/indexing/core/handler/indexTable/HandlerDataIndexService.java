package searchengine.services.indexing.core.handler.indexTable;

import searchengine.dto.sites.IndexDTO;
import searchengine.dto.sites.LemmaDTO;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;

import java.util.List;
import java.util.TreeMap;

public interface HandlerDataIndexService {
    List<IndexDTO> createIndexDTO(TreeMap<Integer, List<LemmaDTO>> lemmas,
                                  List<PageInfo> pageList,
                                  List<Lemma> lemmaList);
}
