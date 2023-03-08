package searchengine.services.writeDataInDB.noSQL;

import searchengine.model.noSQL.CashStatisticsDB;

import java.util.List;

public interface CashStatisticsService {
    void setSiteStatistics();
    void setPageStatistics();
    List<CashStatisticsDB> getStatistics();
}
