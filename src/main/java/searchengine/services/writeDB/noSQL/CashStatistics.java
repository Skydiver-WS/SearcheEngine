package searchengine.services.writeDB.noSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.noSQL.CashStatisticsDB;
import searchengine.repository.noSQL.CashStatisticsRepository;

import java.util.List;

@Service
public class CashStatistics implements CashStatisticsService {
  @Autowired
  private CashStatisticsRepository cashRepository;


    @Override
    public void setSiteStatistics(SiteDTO site) {
        CashStatisticsDB cash = new CashStatisticsDB();
        cash.setId(site.getIdSite());
        cash.setName(site.getName());
        cash.setUrl(site.getUrl());
        cash.setStatus(site.getStatus().toString());
        cash.setStatusTime(site.getTime());
        cashRepository.save(cash);
    }

    @Override
    public void setPageStatistics(SiteDTO site) {
        CashStatisticsDB cash = cashRepository.findById(site.getIdSite()).get();
        cash.setStatusTime(site.getTime());
        cash.setStatus(site.getStatus().toString());
        cash.setPages(site.getPageDTO().size());
        cashRepository.save(cash);
    }

    @Override
  public List<CashStatisticsDB> getStatistics() {
    return cashRepository.findAll();
  }
}
