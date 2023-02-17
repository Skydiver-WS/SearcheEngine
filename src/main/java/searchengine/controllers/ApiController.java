package searchengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.repository.nosql.CashStatisticsRepository;
import searchengine.services.indexing.startIndexing.IndexingService;
import searchengine.services.statistics.extract.StatisticsService;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private IndexingService indexingService;
    @Autowired
    CashStatisticsRepository st;

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public HashMap<String, Object> indexing() {
        return indexingService.getIndexing();
    }
    @GetMapping("/stopIndexing")
    public HashMap<String, Object> stopIndexing(){
        return indexingService.getIndexing();
    }

}
