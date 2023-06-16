package searchengine.services.writedatadb.nossql;

import searchengine.model.sql.SiteInfo;
import searchengine.model.nosql.CashStatisticsDB;

import java.util.List;

public interface CashStatisticsService {
    void setSiteStatistics(SiteInfo siteInfo);
    void write(CashStatisticsDB cash);

    List<CashStatisticsDB> getStatistics();
}
