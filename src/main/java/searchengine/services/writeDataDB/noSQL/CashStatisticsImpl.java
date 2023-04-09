package searchengine.services.writeDataDB.noSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.SiteInfo;
import searchengine.model.noSQL.CashStatisticsDB;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.noSQL.CashStatisticsRepository;

import java.util.List;

@Service
public class CashStatisticsImpl implements CashStatisticsService {
    @Autowired
    private CashStatisticsRepository cashRepository;
    @Autowired
    private IndexRepository indexRepository;
    @Autowired
    private PageRepository pageRepository;


    @Override
    public void setSiteStatistics(SiteInfo siteInfo) {
        var cash = new CashStatisticsDB();
        cash.setId(siteInfo.getId());
        cash.setName(siteInfo.getName());
        cash.setUrl(siteInfo.getUrl());
        cash.setStatus(siteInfo.getStatus().toString());
        cash.setError(siteInfo.getLastError());
        cashRepository.save(cash);
    }

    @Override
    public void write(CashStatisticsDB cash) {
        cashRepository.deleteById(cash.getId());
        cashRepository.save(cash);
    }


//    @Override
//    public void setPageStatistics(int siteId) {
//        var cash = cashRepository.findById(siteId).orElse(null);
//        if (cash != null) {
//            cash.setPages(pageRepository.countPage(siteId));
//            cashRepository.save(cash);
//        }
//    }
//
//    @Override
//    public void setLemmasStatistics(int siteId) {
//        var cash = cashRepository.findById(siteId).orElse(null);
//        if (cash != null) {
//            Integer countLemmas = indexRepository.getCountLemmas(siteId);
//            cash.setLemmas(countLemmas);
//            cashRepository.save(cash);
//        }
//    }

    @Override
    public List<CashStatisticsDB> getStatistics() {
        return cashRepository.findAll();
    }
}