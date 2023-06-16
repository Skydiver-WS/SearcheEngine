package searchengine.services.indexing.core.handler.indextable;

import org.springframework.stereotype.Component;
import searchengine.dto.sites.IndexDTO;
import searchengine.dto.sites.LemmaDTO;
import searchengine.model.sql.Lemma;
import searchengine.model.sql.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Класс производит обработку входных данных и возвращает список объектов {@link IndexDTO}
 * @author Aleksandr Isaev
 */
@Component
public class HandlerDataIndex implements HandlerDataIndexService {
    /**
     * Метод производит обработку входных данных и возвращает список объектов {@link IndexDTO}:
     * @param lemmas - список содержит информацию об id из таблицы {@link PageInfo}, а так же список объектов {@link IndexDTO} соответтсвующей этому id;
     * @param pageList - список объектов из таблицы {@link PageInfo};
     * @param lemmaList - список объектов из таблицы {@link Lemma};
     * @see Optional<PageInfo> - получает объект {@link PageInfo} по ключу key.
     */
    @Override
    public List<IndexDTO> createIndexDTO(TreeMap<Integer, List<LemmaDTO>> lemmas, List<PageInfo> pageList, List<Lemma> lemmaList) {
        List<IndexDTO> indexDTOList = new ArrayList<>();
        for (Integer key : lemmas.keySet()) {
            Optional<PageInfo> optional = pageList.stream().filter(id -> id.getId() == key).findAny();
            if (optional.isPresent()) {
                List<IndexDTO> list = createIndexDTO(lemmas.get(key), lemmaList);
                indexDTOList.addAll(setPageInfoInIndexDTO(optional.get(), list));
            }
        }
        return indexDTOList;
    }

    /**
     * Метод создаёт объекты {@link IndexDTO}
     * @param lemmaDTOList - список лемм в DTO объекте {@link LemmaDTO};
     * @param lemmaList - список лемм;
     * @see Optional<Lemma> - получает лемму из списка лемм, по параметру lemma.getLemma().
     */
    private List<IndexDTO> createIndexDTO(List<LemmaDTO> lemmaDTOList, List<Lemma> lemmaList) {
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

    /**
     * Метод устанавливает параметр {@link PageInfo} в объекте {@link IndexDTO}.
     * @param pageInfo - данные из таблицы {@link PageInfo};
     * @param lemmaList - список объектов {@link IndexDTO}.
     */
    private List<IndexDTO> setPageInfoInIndexDTO(PageInfo pageInfo, List<IndexDTO> lemmaList) {
        List<IndexDTO> list = new ArrayList<>();
        for (IndexDTO indexDTO : lemmaList) {
            indexDTO.setPageInfo(pageInfo);
            list.add(indexDTO);
        }
        return list;
    }
}
