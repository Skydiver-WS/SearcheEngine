package searchengine.services.writeDataDB.noSQL;

import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.SiteInfo;
import searchengine.model.noSQL.CashStatisticsDB;

import java.util.List;

public interface CashStatisticsService {
    void setSiteStatistics(SiteInfo siteInfo);
    void write(CashStatisticsDB cash);

    List<CashStatisticsDB> getStatistics();
}
