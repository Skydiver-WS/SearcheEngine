package searchengine.services.writeDB.noSQL;

import searchengine.dto.sites.SiteDTO;
import searchengine.model.noSQL.CashStatisticsDB;

import java.util.List;

public interface CashStatisticsService {
    void setSiteStatistics(SiteDTO site);
    void setPageStatistics(SiteDTO site);
    List<CashStatisticsDB> getStatistics();
}
