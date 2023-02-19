package searchengine.services.deleteData.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.deleteData.nosql.DeleteCashService;

import java.util.List;

@Service
public class DeleteData implements DeleteDataService {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private DeleteCashService deleteCashService;

    @Override
    public void delete(SiteDTO siteDTO) {
        deleteSite(siteDTO);
    }

    private void deleteSite(SiteDTO siteDTO) {
        for (SiteInfo siteInfo : getSitesInfo()) {
            if (siteInfo.getUrl().equals(siteDTO.getUrl())) {
                siteDTO.setIdSite(siteInfo.getId());
                int id = siteDTO.getIdSite();
                deletePages(id);
                siteRepository.deleteById(id);
                deleteCashService.delete(id);
            }
        }
    }

    private void deletePages(int siteId) {
        for (PageInfo pageInfo : getPageInfo()) {
            if (pageInfo.getSiteId().getId() == siteId) {
                pageRepository.deleteById(pageInfo.getId());
            }
        }
    }

    private synchronized List<SiteInfo> getSitesInfo() {
        return siteRepository.findAll();
    }

    private synchronized List<PageInfo> getPageInfo() {
        return pageRepository.findAll();
    }
}
