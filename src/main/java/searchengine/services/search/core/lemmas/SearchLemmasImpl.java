package searchengine.services.search.core.lemmas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.services.indexing.core.lemma.LemmaService;

import java.util.*;
@Service
public class SearchLemmasImpl implements SearchLemmasService{
    @Autowired
    private LemmaService lemmaService;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private LemmaRepository lemmaRepository;
    private final double PERCENT_REPEAT = 35;
    @Override
    public FrequencyLemmaDTO[] getFindLemmasSort(String query) {
        Map<String, Integer> listLemma = lemmaService.getListLemmas(query);
        List<FrequencyLemmaDTO> listRepeatLemma = new ArrayList<>();
        double count = pageRepository.count();
        for (String lemma : listLemma.keySet()) {
            double countDtoObj = lemmaRepository.searchMatchingLemmas(lemma).size();
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
