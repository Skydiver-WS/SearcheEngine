package searchengine.services.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.search.*;

import searchengine.model.sql.PageInfo;
import searchengine.services.indexing.core.lemma.LemmaService;
import searchengine.services.search.core.lemmas.DefineFrequencyLemmasService;
import searchengine.services.search.core.lemmas.ListLemmasService;
import searchengine.services.search.core.pages.SearchPagesService;
import searchengine.services.search.core.relative.DefiniteRelevanceService;
import searchengine.services.search.core.result.ResultService;

import java.util.*;

/**
 * Данный класс предназначен для принятия запроса, обработки и выдачи результатов поиска.
 * Зависимости:
 * @see DefineFrequencyLemmasService - сервис определяет частоту повторения лемм и возвращает список.
 * @see SearchPagesService - сервис выполняет поиск и возвращает список DTO объектов {@link FrequencyLemmaDTO}.
 * @see DefiniteRelevanceService - сервис определяет релевантность лемм.
 * @see LemmaService - сервис преобразует слова в их исходную форму
 * @see ListLemmasService - сервис осуществляет поиск лемм в БД Redis и возвращает объект {@link SearchObjectDTO}
 *  содержащий информацию об связанных id в БД MySql.
 * @author Aleksandr Isaev
 */
@Service
public class SearchImpl implements SearchService {
    @Autowired
    private DefineFrequencyLemmasService defineFrequencyLemmasService;
    @Autowired
    private SearchPagesService searchPages;
    @Autowired
    private DefiniteRelevanceService relevanceService;
    @Autowired
    private ResultService resultService;
    @Autowired
    private LemmaService lemmaService;
    @Autowired
    private ListLemmasService listLemmasService;

    /**
     *   Метод получает запрос и возвращает результат.
     *   Метод принимает следующие параметры:
     * @param offset - сдвиг от 0 для постраничного вывода (песли не установлен, то значение по умолчанию равно нулю);
     * @param query - поисковый запрос;
     * @param site - сайт, по которому осуществлять поиск (если не задан, поиск должен происходить по всем проиндексированным сайтам);
     * @param limit - количество результатов, которое необходимо вывести (параметр
     * необязательный; если не установлен, то значение по умолчанию равно
     * 20).
     *   Метод работает следующим образом:
     * @see LemmaService#getListLemmas(String) - на вход подаётся запрос, который разбивается на слова и слова преобразуются в исходную форму (лемма)
     *  и возвращается массив лемм.
     * @see ListLemmasService#getListObject(String[]) - метод осуществляет поиск по БД Redis и возвращает список объектов {@link SearchObjectDTO}
     *   содержащий в себе ссылки на id в БД search_engine.
     * @see DefineFrequencyLemmasService#getFindLemmasSort(String[], List) - метод определяет частоту повторяемости лемм переданной в запросе и 
     *   возвращает отсортированный массив объектов {@link FrequencyLemmaDTO} содержащий информацию о лемме и её частоте повторяемости.
     * @see SearchPagesService#searchPages(FrequencyLemmaDTO[], List) - осуществляет поиск по всей БД, если параметр site не задан и 
     *   возвращает DTO объект {@link FrequencyLemmaDTO} содержащий id из таблицы {@link PageInfo}.
     * @see SearchPagesService#searchPages(FrequencyLemmaDTO[], String, List) - осуществляет поиск только в диапазоне заданного сайта и 
     *   возвращает DTO объект {@link FrequencyLemmaDTO} содержащий id из таблицы {@link PageInfo}.
     * @see DefiniteRelevanceService#getList(List)  - метод определяет относительную и абсолютную релевантность найденыйх результатов
     *   и возвращает отсортированный массив объектов {@link FrequencyLemmaDTO}.
     * @see  ResultService#getResult(RelevanceDTO[]) - метод формирует окончательный результат и возвращает DTO объект {@link ResultDTO}
     *   содержащий:
     *    - site - url сайта;
     *    - siteName - название сайта;
     *    - uri - адресс страницы;
     *    - title - заголвок страницы;
     *    - snippet - фрагмент текста, где найдено совпадение;
     *    - relevance - релевантость.
     */
    @Override
    public ResponseSearchDTO search(int offset, String query, String site,  int limit) {
        Map<String, Integer> listLemma = lemmaService.getListLemmas(query);
        String [] lemmasList = listLemma.keySet().toArray(new String[0]);
        List<SearchObjectDTO> searchObjectDTOList = listLemmasService.getListObject(lemmasList);
        FrequencyLemmaDTO[] lemmas = defineFrequencyLemmasService.getFindLemmasSort(lemmasList, searchObjectDTOList);
        if (lemmas.length > 0) {
            List<FrequencyLemmaDTO> pages = (site != null) ? searchPages.searchPages(lemmas, site, searchObjectDTOList) : searchPages.searchPages(lemmas, searchObjectDTOList);
            RelevanceDTO[] dtoList = relevanceService.getList(pages);
            ResultDTO[] resultDTO = resultService.getResult(dtoList);
            List<ResultDTO> listResultDTO = Arrays.stream(resultDTO).filter(Objects::nonNull).toList();
            ResponseSearchDTO response = new ResponseSearchDTO();
            response.setResult(true);
            response.setCount(listResultDTO.size());
            response.setData(listResultDTO);
            return response;
        } else if (query.length() == 0) {
            ResponseSearchDTO response = new ResponseSearchDTO();
            response.setResult(false);
            response.setError("Задан пустой поисковый запрос");
            return response;
        } else {
            ResponseSearchDTO response = new ResponseSearchDTO();
            response.setResult(false);
            response.setError("По Вашему запросу ничего не найдено");
            return response;
        }
    }


}
