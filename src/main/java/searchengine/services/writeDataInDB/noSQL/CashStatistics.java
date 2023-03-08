package searchengine.services.writeDataInDB.noSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.model.noSQL.CashStatisticsDB;
import searchengine.repository.noSQL.CashStatisticsRepository;

import java.util.List;

@Service
public class CashStatistics implements CashStatisticsService {
  @Autowired
  private CashStatisticsRepository cashRepository;


    @Override
    public void setSiteStatistics() {

    }

    @Override
    public void setPageStatistics() {

    }

    @Override
  public List<CashStatisticsDB> getStatistics() {
    return cashRepository.findAll();
  }
}
