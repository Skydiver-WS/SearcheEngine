package searchengine.services.writedatadb.nossql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.model.sql.SiteInfo;
import searchengine.model.nosql.CashStatisticsDB;
import searchengine.repository.nosql.CashStatisticsRepository;

import java.util.List;

@Service
public class CashStatisticsImpl implements CashStatisticsService {
    @Autowired
    private CashStatisticsRepository cashRepository;



    @Override
    public synchronized void setSiteStatistics(SiteInfo siteInfo) {
        var cash = new CashStatisticsDB();
        cash.setId(siteInfo.getId());
        cash.setName(siteInfo.getName());
        cash.setUrl(siteInfo.getUrl());
        cash.setStatus(siteInfo.getStatus().toString());
        cash.setError(siteInfo.getLastError());
        cashRepository.save(cash);
    }

    @Override
    public synchronized void write(CashStatisticsDB cash) {
        cashRepository.deleteById(cash.getId());
        cashRepository.save(cash);
    }

    @Override
    public List<CashStatisticsDB> getStatistics() {
        return cashRepository.findAll();
    }
}
