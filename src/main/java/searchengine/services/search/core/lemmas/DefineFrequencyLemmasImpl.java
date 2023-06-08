package searchengine.services.search.core.lemmas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.dto.search.SearchObjectDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import java.util.*;
@Service
public class DefineFrequencyLemmasImpl implements DefineFrequencyLemmasService{
    /**
     * Данный класс предназначен для определения частоты повторения лемм.
     */
    @Autowired
    private PageRepository pageRepository;

    private final double PERCENT_REPEAT = 80;

    /**
     *  Метод определяет частоту повторяемости лемм, создаёт список DTO объектов {@link FrequencyLemmaDTO}
     * и сортирует их в порядке возрастания частоты.
     * @param lemmas - массив лемм из поискового запроса.
     * @param searchObjectDTOList - список объектов {@link SearchObjectDTO} полученные из БД Redis.
     *  Работа метода:
     * - count - количество записей в таблице {@link PageInfo};
     * - countDtoObj - количество DTO объектов содержащихся лемм.
     */
    @Override
    public FrequencyLemmaDTO[] getFindLemmasSort(String [] lemmas, List<SearchObjectDTO> searchObjectDTOList) {
        List<FrequencyLemmaDTO> listRepeatLemma = new ArrayList<>();
        double count = pageRepository.count();
        for (String lemma : lemmas) {
            double countDtoObj = searchObjectDTOList.stream().filter(l -> l.getLemma().equals(lemma)).count();
            double repeatLemma = (countDtoObj / count) * 100;
            if (repeatLemma <= PERCENT_REPEAT && countDtoObj != 0) {
                var dto = new FrequencyLemmaDTO();
                dto.setLemma(lemma);
                dto.setRepeat(repeatLemma);
                listRepeatLemma.add(dto);
            }
        }
        return sortLemma(listRepeatLemma);
    }
    private FrequencyLemmaDTO[] sortLemma(List<FrequencyLemmaDTO> listLemmas) {
        FrequencyLemmaDTO[] dto = listLemmas.toArray(new FrequencyLemmaDTO[0]);
        Arrays.sort(dto, Comparator.comparingDouble(FrequencyLemmaDTO::getRepeat));
        return dto;
    }
}
