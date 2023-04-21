package searchengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.search.ResponseSearch;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.repository.noSQL.CashStatisticsRepository;
import searchengine.services.indexing.fullIndexing.IndexingService;
import searchengine.services.stopIndexing.StopIndexingService;
import searchengine.services.indexing.singleIndexing.IndexPageService;
import searchengine.services.search.SearchService;
import searchengine.services.statistics.StatisticsService;

import java.util.HashMap;

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
    private CashStatisticsRepository st;
    @Autowired
    private SearchService searchService;

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public HashMap<String, Object> indexing() {
        return indexingService.startIndexing();
    }

    @GetMapping("/stopIndexing")
    public HashMap<String, Object> stopIndexing() {
        return stopIndexing.stopIndexing();
    }

    @PostMapping("/indexPage")
    public HashMap<String, Object> indexPage(@RequestParam String url) {
        return indexPageService.indexPage(url);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseSearch> search(@RequestParam String query, @RequestParam(required = false) String site,
                                 @RequestParam int offset, @RequestParam int limit){
        return ResponseEntity.ok(searchService.search(query, site, offset, limit));
    }
//    @GetMapping("/search")
//    public ResponseEntity<ResponseSearch> search(@RequestParam String query){
//        return ResponseEntity.ok(searchService.search(query));
//    }

}
