package searchengine.services.deleteData.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.deleteData.nosql.DeleteCashService;
import searchengine.services.indexing.IndexingImpl;
import searchengine.services.writeDB.SQL.WriteSiteTable;

import java.util.List;

@Service
public class DeleteData implements DeleteDataService {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private DeleteCashService deleteCashService;
    @Autowired
    private WriteSiteTable writeSiteTable;

    @Override
    public void delete(SiteDTO siteDTO) {
        for (SiteInfo siteInfo : getSitesInfo()) {
            if (siteInfo.getUrl().equals(siteDTO.getUrl())) {
                siteDTO.setIdSite(siteInfo.getId());
                deleteSite(siteDTO);
                //cashStatistics.setPageStatistics(siteDTO); TODO реализовать подгрузку в кэш из бд, пока хз как
            }
        }
    }

    private void deleteSite(SiteDTO siteDTO) {
        writeSiteTable.setStatusIndexing(siteDTO);
        if(deletePages(siteDTO)){
            siteRepository.deleteById(siteDTO.getIdSite());
            deleteCashService.delete(siteDTO.getIdSite());
        }
    }

    private boolean deletePages(SiteDTO siteDTO) {
        for (PageInfo pageInfo : getPageInfo()) {
            if (!IndexingImpl.isAliveThread()) {
                return false;
            } else if (pageInfo.getSiteId().getId() == siteDTO.getIdSite()) {
                pageRepository.deleteById(pageInfo.getId());
            }
        }
        return true;
    }

    private synchronized List<SiteInfo> getSitesInfo() {
        return siteRepository.findAll();
    }

    private synchronized List<PageInfo> getPageInfo() {
        return pageRepository.findAll();
    }
}
