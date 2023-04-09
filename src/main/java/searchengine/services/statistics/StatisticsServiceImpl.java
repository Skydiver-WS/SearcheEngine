package searchengine.services.statistics;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
  @Autowired
  private final PageRepository pageRepository;
  @Autowired
  private final IndexRepository indexRepository;

  @Override
  @SneakyThrows
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
    for (SiteInfo siteInfo : siteInfoList) {
      var cash = cashList.stream().filter(c -> siteInfo.getUrl().equals(c.getUrl())).findAny().orElse(null);
      Long mysqlTime = time(siteInfo.getStatusTime());
      Long redisTime = time(cash != null ? cash.getStatusTime() : null);
      if (cash != null && mysqlTime.equals(redisTime)) {
          var item = new DetailedStatisticsItem();
          item = detailedStatistics(cash);
          detailed.add(item);
      } else {
        var newCash = createCash(siteInfo);
        cashStatistics.write(newCash);
        detailed.add(detailedStatistics(newCash));
      }
    }
    cashList = cashStatistics.getStatistics();
    int sumPages = cashList.stream().filter(p -> p.getPages() != null).mapToInt(CashStatisticsDB::getPages).sum();
    int sumLemmas = cashList.stream().filter(l -> l.getLemmas()  != null).mapToInt(CashStatisticsDB::getLemmas).sum();
    total.setPages(sumPages);
    total.setLemmas(sumLemmas);
    StatisticsResponse response = new StatisticsResponse();
    StatisticsData data = new StatisticsData();
    data.setTotal(total);
    data.setDetailed(detailed);
    response.setStatistics(data);
    response.setResult(true);
    return response;
  }

  private Long time(LocalDateTime time) {
    if (time != null){
      ZonedDateTime zdt = ZonedDateTime.of(time, ZoneId.systemDefault());
      return zdt.toInstant().toEpochMilli();
    } else {
      return 0L;
    }
  }

  private DetailedStatisticsItem detailedStatistics(CashStatisticsDB cash) {
    var item = new DetailedStatisticsItem();
    item.setName(cash.getName());
    item.setUrl(cash.getUrl());
    int pages = cash.getPages() == null ? 0 : cash.getPages();
    int lemmas = cash.getLemmas() == null ? 0 : cash.getLemmas();
    long time = time(cash.getStatusTime());
    item.setPages(pages);
    item.setLemmas(lemmas);
    item.setStatus(cash.getStatus());
    item.setError(cash.getError());
    item.setStatusTime(time);
    return item;
  }

  private CashStatisticsDB createCash(SiteInfo siteInfo) {
    var cash = new CashStatisticsDB();
    int id = siteInfo.getId();
    cash.setId(id);
    cash.setUrl(siteInfo.getUrl());
    cash.setName(siteInfo.getName());
    cash.setError(siteInfo.getLastError());
    cash.setStatus(siteInfo.getStatus().toString());
    cash.setStatusTime(siteInfo.getStatusTime());
    cash.setPages(pageRepository.countPage(id));
    cash.setLemmas(indexRepository.getCountLemmas(id));
    return cash;
  }

}
