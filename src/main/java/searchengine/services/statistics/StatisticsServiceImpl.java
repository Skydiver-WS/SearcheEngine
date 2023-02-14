package searchengine.services.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.dto.statistics.DetailedStatisticsItem;
import searchengine.dto.statistics.StatisticsData;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.dto.statistics.TotalStatistics;
import searchengine.model.PageInfo;
import searchengine.model.SiteInfo;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

  private final Random random = new Random();
  private final SitesList sites;
  @Autowired
  private SiteRepository siteRepository;
  @Autowired
  private PageRepository pageRepository;

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
    List<SiteInfo> sitesList = siteRepository.findAll();
    List<PageInfo> pageList = pageRepository.findAll();
    int sum = pageList.size();
    for (SiteInfo site : sitesList) {
      DetailedStatisticsItem item = new DetailedStatisticsItem();
      item.setName(site.getName());
      item.setUrl(site.getUrl());
      int lemmas = sum * random.nextInt(1_000);//TODO: не забыть исправить
      int pageCount = listByIdSite(site, pageList).size();
      item.setPages(pageCount);
      item.setLemmas(lemmas);
      item.setStatus(site.getStatus().name());
      //item.setError(errors[i % 3]);
      item.setStatusTime(site.getStatusTime());
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

  private List<PageInfo> listByIdSite(SiteInfo site, List<PageInfo> listPage) {
      List<PageInfo> list = new ArrayList<>();
    for (PageInfo pages : listPage) {
        if(pages.getSiteId().equals(site)){
            list.add(pages);
        }
    }
    return list;
  }
}
