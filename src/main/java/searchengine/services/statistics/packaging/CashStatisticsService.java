package searchengine.services.statistics.packaging;

import searchengine.dto.sites.SiteDTO;
import searchengine.model.nosql.CashStatisticsDB;

import java.util.List;

public interface CashStatisticsService {
    void setSiteStatistics(SiteDTO site);
    void setPageStatistics(SiteDTO site);
    List<CashStatisticsDB> getStatistics();
}
