package searchengine.services.writeDataDB.noSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.SiteInfo;
import searchengine.model.noSQL.CashStatisticsDB;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.noSQL.CashStatisticsRepository;

import java.util.List;

@Service
public class CashStatisticsImpl implements CashStatisticsService {
    @Autowired
    private CashStatisticsRepository cashRepository;
    @Autowired
    private IndexRepository indexRepository;


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
    public void setPageStatistics(SiteDTO siteDTO) {
        var cash = cashRepository.findById(siteDTO.getSiteInfo().getId()).orElse(null);
        if (cash != null) {
            cash.setPages(siteDTO.getPageDTOList().size());
            cashRepository.save(cash);
        }
    }

    @Override
    public void setLemmasStatistics(SiteInfo siteInfo) {
        var cash = cashRepository.findById(siteInfo.getId()).orElse(null);
        if (cash != null) {
            Integer countLemmas = indexRepository.getCountLemmas(siteInfo.getId());
            cash.setLemmas(countLemmas);
            cashRepository.save(cash);
        }
    }

    @Override
    public List<CashStatisticsDB> getStatistics() {
        return cashRepository.findAll();
    }
}
