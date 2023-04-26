package searchengine.services.search.core.lemmas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.dto.search.SearchObjectDTO;
import searchengine.model.noSQL.CashLemmas;
import searchengine.repository.noSQL.CashLemmasRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchCashLemmasImpl implements SearchCashLemmasService {
    @Autowired
    private CashLemmasRepository cashLemmasRepository;

    @Override
    public List<SearchObjectDTO> listLemmas(String lemma) {
        List<CashLemmas> list = cashLemmasRepository.findByLemma(lemma);
        List<SearchObjectDTO> dtoList = new ArrayList<>();
        list.stream().map(dto -> {
                    SearchObjectDTO searchObjectDTO = new SearchObjectDTO();
                    searchObjectDTO.setIndexId(dto.getId());
                    searchObjectDTO.setLemma(dto.getLemma());
                    searchObjectDTO.setFrequency(dto.getFrequency());
                    searchObjectDTO.setRank(dto.getRank());
                    searchObjectDTO.setPageId(dto.getPageId());
                    searchObjectDTO.setSiteId(dto.getSiteId());
                    searchObjectDTO.setLemmaId(dto.getLemmaId());
                    return searchObjectDTO;
                }).forEach(dtoList::add);
        return dtoList;
    }
}
