package searchengine.services.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.SitesList;
import searchengine.dto.statistics.DetailedStatisticsItem;
import searchengine.dto.statistics.StatisticsData;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.dto.statistics.TotalStatistics;
import searchengine.model.nosql.CashStatisticsDB;
import searchengine.model.sql.PageInfo;
import searchengine.model.sql.SiteInfo;
import searchengine.repository.nosql.CashStatisticsRepository;
import searchengine.repository.sql.PageRepository;
import searchengine.repository.sql.SiteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final Random random = new Random();
    private final SitesList sites;
    @Autowired
    private CashStatisticsRepository cashStatistics;

    @Override
    public StatisticsResponse getStatistics() {
        String[] statuses = {"INDEXED", "FAILED", "INDEXING"};
        String[] errors = {
                "Ошибка индексации: главная страница сайта не доступна",
                "Ошибка индексации: сайт не доступен",
                ""
        };

        TotalStatistics total = new TotalStatistics();
        total.setSites(sites.getSites().size());
        total.setIndexing(true);

        List<DetailedStatisticsItem> detailed = new ArrayList<>();
        List<CashStatisticsDB> cashList = cashStatistics.findAll();
        int sum = 0;
        for (CashStatisticsDB statistics : cashList) {
            sum += statistics.getPages();
            DetailedStatisticsItem item = new DetailedStatisticsItem();
            item.setName(statistics.getName());
            item.setUrl(statistics.getUrl());
            int lemmas = sum * random.nextInt(1_000);//TODO: не забыть исправить
            int pageCount = statistics.getPages();
            item.setPages(pageCount);
            item.setLemmas(lemmas);
            item.setStatus(statistics.getStatus());
            //item.setError(errors[i % 3]);
            item.setStatusTime(statistics.getStatusTime());
            total.setPages(sum);
            total.setLemmas(total.getLemmas() + lemmas);
            detailed.add(item);
        }

        StatisticsResponse response = new StatisticsResponse();
        StatisticsData data = new StatisticsData();
        data.setTotal(total);
        data.setDetailed(detailed);
        response.setStatistics(data);
        response.setResult(true);
        return response;
    }
}
