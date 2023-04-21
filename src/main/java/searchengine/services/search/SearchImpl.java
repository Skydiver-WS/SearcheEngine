package searchengine.services.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.search.*;

import searchengine.services.search.core.lemmas.SearchLemmasService;
import searchengine.services.search.core.pages.SearchPagesService;
import searchengine.services.search.core.relative.DefiniteRelevanceService;
import searchengine.services.search.core.result.ResultService;

import java.util.*;

@Service
public class SearchImpl implements SearchService {
    @Autowired
    private SearchLemmasService searchLemmas;
    @Autowired
    private SearchPagesService searchPages;
    @Autowired
    private DefiniteRelevanceService relevanceService;
    @Autowired
    private ResultService resultService;

    @Override
    public ResponseSearch search(String query, String site, int offset, int limit) {
        FrequencyLemmaDTO[] lemmas = searchLemmas.getFindLemmasSort(query);
        if (lemmas.length > 0) {
            List<FrequencyLemmaDTO> pages = site != null ? searchPages.searchPages(lemmas, site) : searchPages.searchPages(lemmas);//TODO здесь баг при выборе сайта для поиска
            RelevanceDTO[] dtoList = relevanceService.getList(pages);
            ResultDTO[] resultDTO = resultService.getResult(dtoList);
            ResponseSearch response = new ResponseSearch();
            response.setResult(true);
            response.setCount(resultDTO.length);
            response.setData(List.of(resultDTO));
            return response;
        } else if (query.length() == 0) {
            ResponseSearch response = new ResponseSearch();
            response.setResult(false);
            response.setError("Задан пустой поисковый запрос");
            return response;
        } else {
            ResponseSearch response = new ResponseSearch();
            response.setResult(false);
            response.setError("По Вашему запросу ничего не найдено");
            return response;
        }
    }


}
