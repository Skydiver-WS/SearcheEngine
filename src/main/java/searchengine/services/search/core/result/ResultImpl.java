package searchengine.services.search.core.result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.dto.search.RelevanceDTO;
import searchengine.dto.search.ResultDTO;
import searchengine.model.sql.PageInfo;
import searchengine.repository.sql.PageRepository;
import searchengine.services.search.core.result.multithredquery.MultiThreadQuery;
import searchengine.services.search.core.result.snippet.SnippetService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

/**
 * Данный класс преназначен для сбора всех ранее полученных данных в DTO объект {@link ResultDTO}.
 * Зависимости:
 * @see PageRepository - репозиторий для совершения опрераций с таблицой {@link PageInfo}.
 * @see SnippetService - сервис для формирования сниппетов (фрагментов текста, где найдены совпадения).
 */

@Component
public class ResultImpl implements ResultService {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SnippetService snippetService;

    /**
     *  Метод преназначен для сбора всех ранее полученных данных в DTO объект {@link ResultDTO}.
     * @param dto - массив объектов {@link RelevanceDTO}.
     *  Метод работает следующим образом:
     * - из массива объектов {@link RelevanceDTO} извлекаются значения pageId;
     * - далее передаётся в метод {@link #pageInfoList(List)} который с помощью {@link ForkJoinPool}(экспериментальная функция)
     * извлекает объекты {@link PageInfo} из БД;
     * - с помощью Stream API происходит создание массива объектов {@link ResultDTO}.
     */
    @Override
    public ResultDTO[] getResult(RelevanceDTO[] dto) {
        List<Integer> listId = new ArrayList<>();
        Arrays.stream(dto).map(RelevanceDTO::getPageId).forEach(listId::add);
        //List<PageInfo> pageInfoList = pageRepository.findAllById(listId);
        List<PageInfo> pageInfoList = pageInfoList(listId);
        return Arrays.stream(dto).map(o -> {
            PageInfo pageInfo = pageInfoList.stream().filter(id -> o.getPageId() == id.getId()).findFirst().orElse(null);
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setSite(pageInfo.getSiteId().getUrl());
            resultDTO.setSiteName(pageInfo.getSiteId().getName());
            resultDTO.setUri(pageInfo.getPath());
            resultDTO.setRelevance(o.getRelRelevance());
            resultDTO.setTitle(getTitlePage(pageInfo));
            resultDTO.setSnippet(snippetService.getSnippet(pageInfo, o.getListFrequencyLemmaDTOList()));
            return resultDTO.getSnippet().length() == 0 ? null : resultDTO;
        }).toArray(ResultDTO[]::new);
    }

    private String getTitlePage(PageInfo pageInfo) {
        Document doc = Jsoup.parse(pageInfo.getContent());
        return doc.title();
    }

    private List<PageInfo> pageInfoList(List<Integer> listId) {
        List<MultiThreadQuery> listQuery = new ArrayList<>();
        List<PageInfo> pageInfoList = new ArrayList<>();
        for (Integer id : listId) {
            MultiThreadQuery multiQuery = new MultiThreadQuery(pageRepository, id);
            multiQuery.fork();
            listQuery.add(multiQuery);
            Logger.getLogger(ResultImpl.class.getName()).info("Fork complete");
        }
        for (MultiThreadQuery query : listQuery) {
            pageInfoList.add(query.join());
            Logger.getLogger(ResultImpl.class.getName()).info("Join complete");
        }
        return pageInfoList;
    }

//    private List<PageInfo> pageInfoList(List<Integer> listId) {
//        List<MultiThreadQuery> listQuery = new ArrayList<>();
//        List<PageInfo> pageInfoList = new ArrayList<>();
//        List<List<Integer>> list = arrayDivision(listId);
//        for (List<Integer> id : list) {
//            MultiThreadQuery multiQuery = new MultiThreadQuery(pageRepository, id);
//            multiQuery.fork();
//            listQuery.add(multiQuery);
//            Logger.getLogger(ResultImpl.class.getName()).info("Fork complete");
//        }
//        for (MultiThreadQuery query : listQuery) {
//            pageInfoList.addAll(query.join());
//            Logger.getLogger(ResultImpl.class.getName()).info("Join complete");
//        }
//        return pageInfoList;
//    }
//
//    private List<List<Integer>> arrayDivision(List<Integer> listId) {
//        int core = Runtime.getRuntime().availableProcessors();
//        int middle = listId.size() / core;
//        double remainder = listId.size() % core;
//        List<Integer> integerList = new ArrayList<>();
//        List<List<Integer>> listObject = new ArrayList<>();
//        for (int i = 0; i < core; i++) {
//            integerList.add(middle);
//        }
//        for (int i = 0; i < remainder; i++) {
//            integerList.set(i, integerList.get(i) + 1);
//        }
//        int i = 0;
//        for (Integer count : integerList) {
//            listObject.add(listId.subList(i, count + i));
//            i = count;
//        }
//        return listObject;
//    }
}
