package searchengine.services.search.core.relative;

import org.springframework.stereotype.Service;
import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.dto.search.RelevanceDTO;

import java.util.*;

/**
 * Класс определяет относительную и абсолютную релевантность найденыйх результатов
 * и возвращает отсортированный массив объектов {@link FrequencyLemmaDTO}.
 * @author Aleksandr Isaev
 */
@Service
public class DefiniteRelevanceImpl implements DefiniteRelevanceService {
    /**
     *  Метод составляет массив объектов {@link RelevanceDTO} содержащий информацию о релевантности.
     * @param list - список объектов {@link FrequencyLemmaDTO}
     *  Метод работает следующим образом:
     * - создаётся объект {@link RelevanceDTO};
     * @see RelevanceDTO#setListFrequencyLemmaDTOList(List) - с помощью Strem API происходит группировка
     * лемм по параметру pageId и фильтрация по отсутсвию дубликатов.
     * Далее происходит проверка, о том что список ListFrequencyLemmaDTOList не пустой и после этого происходит
     * добавление в список listDTO.
     * @see #getRelevance(List) - метод производит расчёт релевантности добавление в объекты {@link RelevanceDTO}
     * и формирует массив.
     * Происходит возврат отсортированного массива объектов {@link RelevanceDTO}.
     */
    @Override
    public RelevanceDTO[] getList(List<FrequencyLemmaDTO> list) {
        List<RelevanceDTO> listDTO = new ArrayList<>();
        HashSet<Integer> notDuplicateDtoObj = new HashSet<>();
        for (FrequencyLemmaDTO dto : list){
            RelevanceDTO obj = new RelevanceDTO();
            obj.setListFrequencyLemmaDTOList(list.stream()
                    .filter(d -> d.getPageId() == dto.getPageId())
                    .filter(d -> !notDuplicateDtoObj.contains(d.getPageId()))
                    .toList());
            int pageId = dto.getPageId();
            if (!obj.getListFrequencyLemmaDTOList().isEmpty()){
                obj.setPageId(pageId);
                listDTO.add(obj);
            }
            notDuplicateDtoObj.add(pageId);
        }
        RelevanceDTO[] obj = getRelevance(listDTO).toArray(new RelevanceDTO[0]);
        Arrays.sort(obj, Comparator.comparingDouble(RelevanceDTO::getRelRelevance).reversed());
        return obj;
    }
    private List<RelevanceDTO> getRelevance (List<RelevanceDTO> listDTO) {
        listDTO.forEach(dto -> dto.setAbsRelevance(dto.getListFrequencyLemmaDTOList().stream()
                .mapToDouble(FrequencyLemmaDTO::getRank)
                .sum()));
    double absRelMax = listDTO.stream()
            .mapToDouble(RelevanceDTO::getAbsRelevance)
            .max()
            .orElse(Double.NaN);
       listDTO.forEach(dto -> dto.setRelRelevance(dto.getAbsRelevance() / absRelMax));
    return listDTO;
    }
}
