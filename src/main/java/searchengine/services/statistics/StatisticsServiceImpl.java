package searchengine.services.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.site.SitesList;
import searchengine.dto.statistics.*;
import searchengine.model.SQL.SiteInfo;
import searchengine.model.noSQL.CashStatisticsDB;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.writeDataDB.noSQL.CashStatisticsService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final SitesList sites;
    @Autowired
    private CashStatisticsService cashStatistics;
    @Autowired
    private SiteRepository siteRepository;

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
        List<CashStatisticsDB> cashList = cashStatistics.getStatistics();
        List<SiteInfo> siteInfoList = siteRepository.findAll();
        int sum = 0;
        for (CashStatisticsDB statistics : cashList) {
            sum += statistics.getPages();
            DetailedStatisticsItem item = new DetailedStatisticsItem();
            item.setName(statistics.getName());
            item.setUrl(statistics.getUrl());
            int lemmas = statistics.getLemmas();//TODO: не забыть исправить
            int pageCount = statistics.getPages();
            item.setPages(pageCount);
            item.setLemmas(lemmas);
            item.setStatus(statistics.getStatus());
            item.setStatusTime(time(statistics.getStatusTime()));
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

//    @Autowired
//    private SiteRepository siteRepository;
//    @Autowired
//    private IndexRepository indexRepository;
//    private final SitesList sites;

    //    @Override
//    public StatisticsResponse getStatistics() {
//        String[] statuses = {"INDEXED", "FAILED", "INDEXING"};
//        String[] errors = {
//                "Ошибка индексации: главная страница сайта не доступна",
//                "Ошибка индексации: сайт не доступен",
//                ""
//        };
//
//        TotalStatistics total = new TotalStatistics();
//        total.setSites(sites.getSites().size());
//        total.setIndexing(true);
//        List<DetailedStatisticsItem> detailed = new ArrayList<>();
//        List<SiteInfo> list = siteRepository.findAll();
//        //List<PageStatisticsDTO> pageStatisticsDTOList = siteRepository.getPageStatisticsHQLQuery();
//        for (SiteInfo siteInfo : list) {
//            DetailedStatisticsItem item = new DetailedStatisticsItem();
//            PageStatisticsDTO pageSt = siteRepository.getPageStatisticsHQLQuery(siteInfo.getId());
////            PageStatisticsDTO pageSt = pageStatisticsDTOList.stream()
////                    .filter(p -> p.getSiteId() == siteInfo.getId())
////                    .findFirst()
////                    .orElse(null);
//            item.setName(siteInfo.getName());
//            item.setUrl(siteInfo.getUrl());
//            int pages =  pageSt == null ? 0 : Math.toIntExact(pageSt.getCountPage());
//            int lemmas = (indexRepository.getCountLemmas(siteInfo.getId()) == null) ? 0 : indexRepository.getCountLemmas(siteInfo.getId());
//            long time = time(siteInfo.getStatusTime());
//            item.setPages(pages);
//            item.setLemmas(lemmas); // TODO узкое место, задействовать REDIS
//            item.setStatus(siteInfo.getStatus().toString());
//            item.setError(siteInfo.getLastError());
//            item.setStatusTime(time);
//            total.setPages(total.getPages() + pages);
//            total.setLemmas(total.getLemmas() + lemmas);
//            detailed.add(item);
//        }
//
//        StatisticsResponse response = new StatisticsResponse();
//        StatisticsData data = new StatisticsData();
//        data.setTotal(total);
//        data.setDetailed(detailed);
//        response.setStatistics(data);
//        response.setResult(true);
//        return response;
//    }
    private long time(LocalDateTime time) {
        ZonedDateTime zdt = ZonedDateTime.of(time, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }
}
