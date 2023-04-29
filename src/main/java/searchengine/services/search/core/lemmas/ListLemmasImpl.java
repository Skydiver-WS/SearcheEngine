package searchengine.services.search.core.lemmas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.dto.search.SearchObjectDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class ListLemmasImpl implements ListLemmasService{
    @Autowired
    private SearchCashLemmasService cashLemmasService;
    @Override
    public List<SearchObjectDTO> getListObject(String[] lemmas) {
        List<SearchObjectDTO> list = new ArrayList<>();
        Arrays.stream(lemmas).toList().stream()
                .map(l -> cashLemmasService.listLemmas(l))
                .forEach(list::addAll);
        return list;
    }
}
