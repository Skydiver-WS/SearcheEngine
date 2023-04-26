package searchengine.services.search.core.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.dto.search.SearchObjectDTO;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.search.core.lemmas.SearchCashLemmasService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class SearchPagesImpl implements SearchPagesService {

    @Autowired
    private LemmaRepository lemmaRepository;

    @Autowired
    private SearchCashLemmasService searchCashLemmasService;
    @Autowired
    private SiteRepository siteRepository;


    @Override
    public List<FrequencyLemmaDTO> searchPages(FrequencyLemmaDTO[] list, List<SearchObjectDTO> searchObjectDTOList) {
        //List<SearchObjectDTO> pagesFound = lemmaRepository.searchMatchingLemmas(list[0].getLemma());
        List<SearchObjectDTO> pagesFound = searchObjectDTOList.stream().filter(l -> l.getLemma().equals(list[0].getLemma())).toList();
        return getPages(list, pagesFound, searchObjectDTOList);
    }

    @Override
    public List<FrequencyLemmaDTO> searchPages(FrequencyLemmaDTO[] list, String site, List<SearchObjectDTO> searchObjectDTOList) {
        int siteId = Objects.requireNonNull(siteRepository.getSiteInfo(site).orElse(null)).getId();
        List<SearchObjectDTO> filterListBySiteId = searchObjectDTOList.stream().filter(l -> l.getSiteId() == siteId).toList();
        List<SearchObjectDTO> pagesFound = filterListBySiteId.stream().filter(l -> l.getLemma().equals(list[0].getLemma())).toList();
        return getPages(list, pagesFound, filterListBySiteId);
    }

    private List<FrequencyLemmaDTO> getPages(FrequencyLemmaDTO[] list, List<SearchObjectDTO> pagesFound, List<SearchObjectDTO> searchObjectDTOList) {
        List<FrequencyLemmaDTO> dtoList = new ArrayList<>();
        for (var dto : pagesFound) {
            var obj = createNewObj(list[0], dto);
            dtoList.add(obj);
            Arrays.stream(list, 1, list.length)
                    .flatMap(l -> searchObjectDTOList.stream()
                            .filter(lemma -> lemma.getLemma().equals(l.getLemma()))
                            .filter(lemma -> lemma.getPageId() == dto.getPageId())
                            .map(s -> createNewObj(l, s)))
                    .forEach(dtoList::add);
        }
        return dtoList;
    }

//    private List<FrequencyLemmaDTO> getPages(FrequencyLemmaDTO[] list, List<SearchObjectDTO> pagesFound) {
//        List<FrequencyLemmaDTO> dtoList = new ArrayList<>();
//        for (var dto : pagesFound) {
//            var obj = createNewObj(list[0], dto);
//            dtoList.add(obj);
//            Arrays.stream(list, 1, list.length)
//                    .flatMap(l -> lemmaRepository.searchMatchingLemmas(l.getLemma(), dto.getPageId()).stream()
//                            .map(s -> createNewObj(l, s)))
//                    .forEach(dtoList::add);
//        }
//        return dtoList;
//    }

    private FrequencyLemmaDTO createNewObj(FrequencyLemmaDTO obj1, SearchObjectDTO obj2) {
        var newObj = new FrequencyLemmaDTO();
        newObj.setRepeat(obj1.getRepeat());
        newObj.setLemma(obj1.getLemma());
        newObj.setPageId(obj2.getPageId());
        newObj.setRank(obj2.getRank());
        return newObj;
    }
}
