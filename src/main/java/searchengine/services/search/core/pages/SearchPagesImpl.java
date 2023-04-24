package searchengine.services.search.core.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.dto.search.SearchObjectDTO;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.SiteRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchPagesImpl implements SearchPagesService {

    @Autowired
    private LemmaRepository lemmaRepository;
    @Autowired
    private SiteRepository siteRepository;

    @Override
    public List<FrequencyLemmaDTO> searchPages(FrequencyLemmaDTO[] list) {
        List<SearchObjectDTO> pagesFound = lemmaRepository.searchMatchingLemmas(list[0].getLemma());
        return getPages(list, pagesFound);
    }

    @Override
    public List<FrequencyLemmaDTO> searchPages(FrequencyLemmaDTO[] list, String site){
        List<SearchObjectDTO> pagesFound = lemmaRepository.searchMatchingLemmas(list[0].getLemma(), site);
        return getPages(list, pagesFound);
    }

    private List<FrequencyLemmaDTO> getPages(FrequencyLemmaDTO[] list, List<SearchObjectDTO> pagesFound){
        List<FrequencyLemmaDTO> dtoList = new ArrayList<>();
        for (var dto : pagesFound) {
            var obj = createNewObj(list[0], dto);
            dtoList.add(obj);
            Arrays.stream(list, 1, list.length)
                    .flatMap(l -> lemmaRepository.searchMatchingLemmas(l.getLemma(), dto.getPageId()).stream()
                            .map(s -> createNewObj(l, s)))
                    .forEach(dtoList::add);
        }
        return dtoList;
    }

    private FrequencyLemmaDTO createNewObj(FrequencyLemmaDTO obj1, SearchObjectDTO obj2) {
        var newObj = new FrequencyLemmaDTO();
        newObj.setRepeat(obj1.getRepeat());
        newObj.setLemma(obj1.getLemma());
        newObj.setPageId(obj2.getPageId());
        newObj.setRank(obj2.getRank());
        return newObj;
    }
}
