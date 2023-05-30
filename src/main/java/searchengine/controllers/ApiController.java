package searchengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.search.ResponseSearchDTO;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.repository.noSQL.CashStatisticsRepository;
import searchengine.services.indexing.fullIndexing.IndexingService;
import searchengine.services.stopIndexing.StopIndexingService;
import searchengine.services.indexing.singleIndexing.IndexPageService;
import searchengine.services.search.SearchService;
import searchengine.services.statistics.StatisticsService;

import java.util.HashMap;

/**
 * Данный контроллер содержит методы которые выполняют следующие функции:
 *  - возвращает статистику;
 *  - запускает индескацию;
 *  - останавливает индексацию;
 *  - передаёт поисковый запрос и получает результат.
 * Включенный зависимости:
 * @see StatisticsService - сервис предназначен для сбора статистики;
 * @see IndexingService - сервис запускает индексацию;
 * @see StopIndexingService - сервис останавливает индексацию;
 * @see IndexPageService - сервис запускает индексацию одиночной страницы;
 * @see SearchService - сервис выполняет поиск по заданному запросу;
 * @author Aleksandr Isaev
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private IndexingService indexingService;
    @Autowired
    private StopIndexingService stopIndexing;
    @Autowired
    private IndexPageService indexPageService;
    @Autowired
    private SearchService searchService;


    /**
     * @see #statistics() - метод вызывает сбор статистики из базы данных MySQL, передаёт его в базу данных Redis,
     * и возвращает ответ в формате JSON.
     * @see StatisticsResponse содержит:
     * 1. result
     * 2. statistics - содержит данные:
     *   2.1 detailed - cтатистика по каждому сайту:
     *      2.1.1 url - адресс сайта.
     *      2.1.2 name - название сайта.
     *      2.1.3 status - статус.
     *      2.1.4 pages - количество страниц.
     *      2.1.5 statusTime - время изменения статуса.
     *      2.1.6 error - возможные ошибки.
     *   2.2 total - глобальная статистика:
     *      2.2.1 indexing - результат.
     *      2.2.2 lemmas - общее количество лемм.
     *      2.2.3 pages - общее количество страниц.
     *      2.2.4 sites - общее количество сайтов.
     */

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    /**
     * Данный метод запускает индексацию сайтов указанных в конфигурационном файле.
     */

    @GetMapping("/startIndexing")
    public HashMap<String, Object> indexing() {
        return indexingService.startIndexing();
    }

    /**
     * Данный метод останавливает индексацию.
     */

    @GetMapping("/stopIndexing")
    public HashMap<String, Object> stopIndexing() {
        return stopIndexing.stopIndexing();
    }

    /**
     * Данный метод запускает индексацию переданной страницы.
     * @param url - ссылка передаваемыая в запросе.
     */
    @PostMapping("/indexPage")
    public HashMap<String, Object> indexPage(@RequestParam String url) {
        return indexPageService.indexPage(url);
    }

    /**
     * Данный метод осуществляет поиск по базе данных и возвращает найденный результат
     * @param query - предаваемый запрос
     * @param site - осуществляет поиск по выбранному сайту
     * Метод возвращает следующий результат:
     * 1. result - возвращает true, если результат найден или false - если результат не найден.
     * 2. count - кольчество найденых страниц.
     * 3. error - возможные ошибки.
     * 4. data - данные результата:
     *    4.1 - relevance - релевантность, чем выше тем точнее результат.
     *    4.2 - site - адресс сайта.
     *    4.3 - siteName - название сайта.
     *    4.4 - snippet - часть тексат, где найдено совпадение.
     *    4.5 - title - заголовок.
     *    4.6 - uri - адресс страницы.
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseSearchDTO> search(@RequestParam String query, @RequestParam(required = false) String site,
                                                    @RequestParam int offset, @RequestParam int limit){
        return ResponseEntity.ok(searchService.search(offset, query, site, limit));
    }
}
