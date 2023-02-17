package searchengine.services.deleteData.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.sql.PageInfo;
import searchengine.model.sql.SiteInfo;
import searchengine.repository.sql.PageRepository;
import searchengine.repository.sql.SiteRepository;
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
    @Transactional
    public void delete(String url) {
        deleteSite(url);
    }

    private void deleteSite(String site) {
        for (SiteInfo siteInfo : getSitesInfo()) {
            if (siteInfo.getUrl().equals(site)) {
                deletePages(siteInfo.getId());
                siteRepository.deleteById(siteInfo.getId());
                deleteCashService.delete(siteInfo.getId());
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
