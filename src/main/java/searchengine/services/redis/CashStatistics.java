package searchengine.services.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.model.nosql.CashStatisticsDB;
import searchengine.model.sql.PageInfo;
import searchengine.model.sql.SiteInfo;
import searchengine.repository.nosql.CashStatisticsRepository;
import searchengine.repository.sql.PageRepository;
import searchengine.repository.sql.SiteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CashStatistics implements CashStatisticsService {
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private CashStatisticsRepository cashRepository;

    @Override
    public void statistics() {
        cashRepository.deleteAll();
        List<SiteInfo> siteInfo = siteRepository.findAll();
        List<PageInfo> pageInfo = pageRepository.findAll();
        List<CashStatisticsDB> cashList = new ArrayList<>();
        for (SiteInfo site:siteInfo) {
            CashStatisticsDB cash = new CashStatisticsDB();
            cash.setId(site.getId());
            cash.setName(site.getName());
            cash.setUrl(site.getUrl());
            int sum = countPage(site, pageInfo).size();
            cash.setPages(sum);
            cash.setLemmas(0);//TODO: заглушка
            cash.setError(site.getLastError());
            cash.setStatus(site.getStatus().toString());
            cash.setStatusTime(site.getStatusTime());
            cashList.add(cash);
        }
        cashRepository.saveAll(cashList);
    }
    private List<PageInfo> countPage(SiteInfo site, List<PageInfo> listPage) {
        List<PageInfo> list = new ArrayList<>();
        for (PageInfo pages : listPage) {
            if(pages.getSiteId().equals(site)){
                list.add(pages);
            }
        }
        return list;
    }
}
