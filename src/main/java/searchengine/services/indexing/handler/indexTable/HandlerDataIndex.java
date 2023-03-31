package searchengine.services.indexing.handler.indexTable;

import org.springframework.stereotype.Component;
import searchengine.dto.sites.IndexDTO;
import searchengine.dto.sites.LemmaDTO;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

@Component
public class HandlerDataIndex implements HandlerDataIndexService {
    @Override
    public List<IndexDTO> createIndexDTO(TreeMap<Integer, List<LemmaDTO>> lemmas, List<PageInfo> pageList, List<Lemma> lemmaList) {

        List<IndexDTO> indexDTOList = new ArrayList<>();
        for (Integer key : lemmas.keySet()) {
            Optional<PageInfo> optional = pageList.stream().filter(id -> id.getId() == key).findAny();
            if (optional.isPresent()) {
                List<IndexDTO> list = getLemma(lemmas.get(key), lemmaList);
                indexDTOList.addAll(listIndexDTO(optional.get(), list));
            }
        }
        return indexDTOList;
    }
    private List<IndexDTO> getLemma(List<LemmaDTO> lemmaDTOList, List<Lemma> lemmaList) {
        List<IndexDTO> list = new ArrayList<>();
        for (LemmaDTO lemmaDTO : lemmaDTOList) {
            Optional<Lemma> optional = lemmaList.stream().filter(c -> c.getLemma().equals(lemmaDTO.getLemma())).findAny();
            if (optional.isPresent()) {
                IndexDTO indexDTO = new IndexDTO();
                indexDTO.setLemma(optional.get());
                indexDTO.setRank(lemmaDTO.getCount());
                list.add(indexDTO);
            }
        }
        return list;
    }

    private List<IndexDTO> listIndexDTO(PageInfo pageInfo, List<IndexDTO> lemmaList) {
        List<IndexDTO> list = new ArrayList<>();
        for (IndexDTO indexDTO : lemmaList) {
            indexDTO.setPageInfo(pageInfo);
            list.add(indexDTO);
        }
        return list;
    }
}
