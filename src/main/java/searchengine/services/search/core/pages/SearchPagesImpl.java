package searchengine.services.search.core.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.dto.search.SearchObjectDTO;
import searchengine.model.sql.SiteInfo;
import searchengine.repository.sql.SiteRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Данный предназначен для получения страниц на которых найдены леммы
 * @author Aleksandr Isaev
 */
@Service
public class SearchPagesImpl implements SearchPagesService {
    @Autowired
    private SiteRepository siteRepository;

    /**
     * Метод предназначен для получения объектов {@link FrequencyLemmaDTO}.
     * @param list - запрос который преобразован в массив лемм и отсортирован по частоте повторения лемм в порядке возрастания.
     * @param searchObjectDTOList - список всех объектов {@link SearchObjectDTO} найденых в БД Redis.
     * Метод работает следующим образом:
     *  - pageFound - из список создаётся путём фильтрации общего списка searchObjectDTOList по самой редкой лемме из массива list.
     * @see #getPages(FrequencyLemmaDTO[], List, List) - служит для получения списка объектов {@link FrequencyLemmaDTO}
     */
    @Override
    public List<FrequencyLemmaDTO> searchPages(FrequencyLemmaDTO[] list, List<SearchObjectDTO> searchObjectDTOList) {
        List<SearchObjectDTO> pagesFound = searchObjectDTOList.stream().filter(l -> l.getLemma().equals(list[0].getLemma())).toList();
        return getPages(list, pagesFound, searchObjectDTOList);
    }
    /**
     * Метод предназначен для получения объектов {@link FrequencyLemmaDTO}.
     * @param list - запрос который преобразован в массив лемм и отсортирован по частоте повторения лемм в порядке возрастания.
     * @param site - url сайта по которому необходимо совершить поиск.
     * @param searchObjectDTOList - список всех объектов {@link SearchObjectDTO} найденых в БД Redis.
     * Метод работает следующим образом:
     *  - из таблицы {@link SiteInfo} получаем siteId по параметру site;
     *  - создаётся новый список filterListBySiteId содержащий siteId из списка searchObjectDTOList;
     *  - pageFound - из список создаётся путём фильтрации общего списка searchObjectDTOList по самой редкой лемме из массива list;
     * @see #getPages(FrequencyLemmaDTO[], List, List) - служит для получения списка объектов {@link FrequencyLemmaDTO}.
     */
    @Override
    public List<FrequencyLemmaDTO> searchPages(FrequencyLemmaDTO[] list, String site, List<SearchObjectDTO> searchObjectDTOList) {
        int siteId = Objects.requireNonNull(siteRepository.getSiteInfo(site).orElse(null)).getId();
        List<SearchObjectDTO> filterListBySiteId = searchObjectDTOList.stream().filter(l -> l.getSiteId() == siteId).toList();
        List<SearchObjectDTO> pagesFound = filterListBySiteId.stream().filter(l -> l.getLemma().equals(list[0].getLemma())).toList();
        return getPages(list, pagesFound, filterListBySiteId);
    }

    /**
     *  Метод предназначен для получения списка объектов {@link FrequencyLemmaDTO}
     * @param list - запрос который преобразован в массив лемм и отсортирован по частоте повторения лемм в порядке возрастания.
     * @param pagesFound - список найденых страниц с самой редкой леммой.
     * @param searchObjectDTOList - список всех объектов {@link SearchObjectDTO} найденых в БД Redis.
     *  Метод работает следующим образом:
     * - происходит интерация pagesFound внутри которой создаётся новый объект {@link FrequencyLemmaDTO}
     *   и добавляется в список dtoList.
     * - с помощью Stream API итерируются все элементы массива list за исключением элемента под индексом 0.
     *   В Stream происходит фильтрация списка searchObjectDTOList по лемме которая содержится в масиве list и по id из объекта dto.
     * - далее происходит создание нового объекта {@link FrequencyLemmaDTO} и добавление в список dtoList.
     */
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

    private FrequencyLemmaDTO createNewObj(FrequencyLemmaDTO obj1, SearchObjectDTO obj2) {
        var newObj = new FrequencyLemmaDTO();
        newObj.setRepeat(obj1.getRepeat());
        newObj.setLemma(obj1.getLemma());
        newObj.setPageId(obj2.getPageId());
        newObj.setRank(obj2.getRank());
        return newObj;
    }
}
