package searchengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.repository.noSQL.CashStatisticsRepository;
import searchengine.services.indexing.fullIndexing.IndexingService;
import searchengine.services.indexing.core.stopIndexing.StopIndexingService;
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
    CashStatisticsRepository st;

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
        HashMap<String, Object> test = new HashMap<>();
        test.put("result", false);
        test.put("error", "Данная страница находится за пределами сайтов," +
                "указанных в конфигурационном файле");
        return test;
    }

}
