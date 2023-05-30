package searchengine.services.statistics;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.SitesList;
import searchengine.dto.statistics.*;
import searchengine.model.SQL.SiteInfo;
import searchengine.model.noSQL.CashStatisticsDB;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.writeDataDB.noSQL.CashStatisticsService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Данный метод вызывает сбор статистики из базы данных search_engine, передаёт его в базу данных Redis,
 * и возвращает ответ в формате JSON.
 * StatisticsResponse содержит:
 * 1. result
 * 2. statistics - содержит данные:
 * 2.1 detailed - cтатистика по каждому сайту:
 * 2.1.1 url - адресс сайта.
 * 2.1.2 name - название сайта.
 * 2.1.3 status - статус.
 * 2.1.4 pages - количество страниц.
 * 2.1.5 statusTime - время изменения статуса.
 * 2.1.6 error - возможные ошибки.
 * 2.2 total - глобальная статистика:
 * 2.2.1 indexing - результат.
 * 2.2.2 lemmas - общее количество лемм.
 * 2.2.3 pages - общее количество страниц.
 * 2.2.4 sites - общее количество сайтов.
 *
 * @author Aleksandr Isaev
 */

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

  /**
   * Метод getStatistics() работает следующим образом:
   *  - создаются объекты StatisticsResponse (статистика по сайтам. Далее  SR.) и TotalStatistics (общая статистика. Далее TS)
   *  - в TS  записывается общее количество сайтов указанные в конфигурационном файле;
   *  - создаётся массив List<DetailedStatisticsItem> detailed для сбора статистики по каждому сайту в отдельности;
   *  - из БД Redis берётся вся статистика по сайтам и добавляется в список List<CashStatisticsDB> cashList;
   *  - из БД search_engine берутся все данные из репозитория siteRepository и добавляются в List<SiteInfo> siteInfoList;
   *  - создаётся цикл, где итерируется siteInfoList;
   *  - из списка cashList путём Stream API получаем объект CashStatisticsDB (var cash) в котором URL совпадает с URL
   *    объекта siteInfo;
   *  - Long mysqlTime время полученное из объекта siteInfo;
   *  - Long redisTime время полученное из объекта cash;
   *  - если условие cash != null && mysqlTime.equals(redisTime) выполняется, то объект создаётся объект DetailedStatisticsItem()
   *    и в него добавляются параметры из объекта cash и добавляется в список detailed;
   *  - иначе создаётся новый объект  CashStatisticsDB newCash и добавляются параметры в DetailedStatisticsItem() и в список detailed;
   *  - далее из бд REDIS берётся информация добавляется в TS  в StatisticsData data и возвращается ответ response.
   */
    @Override
    @SneakyThrows
    public StatisticsResponse getStatistics() {
        StatisticsResponse response = new StatisticsResponse();
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
        int sumLemmas = cashList.stream().filter(l -> l.getLemmas() != null).mapToInt(CashStatisticsDB::getLemmas).sum();
        total.setPages(sumPages);
        total.setLemmas(sumLemmas);
        StatisticsData data = new StatisticsData();
        data.setTotal(total);
        data.setDetailed(detailed);
        response.setStatistics(data);
        response.setResult(true);
        return response;
    }

    /**
     *  метод преобразует LocalDateTime в миллисикунды
     * @param time - передаваемый объект LocalDateTime
     */
    private Long time(LocalDateTime time) {
        if (time != null) {
            ZonedDateTime zdt = ZonedDateTime.of(time, ZoneId.systemDefault());
            return zdt.toInstant().toEpochMilli();
        } else {
            return 0L;
        }
    }

    /**
     * Создание объекта DetailedStatisticsItem() и добавление данных из CashStatisticsDB
     * @param cash - данные из БД Redis.
     */
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

    /**
     * Создаёт новый объект CashStatisticsDB.
     * @param siteInfo - донные из БД search_engine таблицы site
     */
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
