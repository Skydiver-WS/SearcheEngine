package searchengine.services.writeDataDB.noSQL;

import searchengine.model.noSQL.CashStatisticsDB;

import java.util.List;

public interface CashStatisticsService {
    void setSiteStatistics();
    void setPageStatistics();
    List<CashStatisticsDB> getStatistics();
}
