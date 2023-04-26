package searchengine.services.search.core.lemmas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.dto.search.SearchObjectDTO;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import java.util.*;
@Service
public class SearchLemmasImpl implements SearchLemmasService{

    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SearchCashLemmasService cashLemmasService;
    @Autowired
    private LemmaRepository lemmaRepository;

    private final double PERCENT_REPEAT = 100;
    @Override
    public FrequencyLemmaDTO[] getFindLemmasSort(String [] lemmas, List<SearchObjectDTO> searchObjectDTOList) {
        List<FrequencyLemmaDTO> listRepeatLemma = new ArrayList<>();
        double count = pageRepository.count();
        for (String lemma : lemmas) {
            //double countDtoObj = lemmaRepository.searchMatchingLemmas(lemma).size();
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
