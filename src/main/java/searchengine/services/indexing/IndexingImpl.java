package searchengine.services.indexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.config.Status;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.PageInfo;
import searchengine.model.SiteInfo;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.writeDB.WritePageDBService;
import searchengine.services.writeDB.WriteSiteDBService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class IndexingImpl implements IndexingService {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private SitesList sitesList;
    @Autowired
    private WriteSiteDBService writeSite;
    @Autowired
    private WritePageDBService writePage;

    @Override
    public boolean getIndexing() {
        for (Site site : sites()) {
            new Thread(() -> {
                deleteSite(site.getUrl());
                parse(site);
            }).start();
        }
        return false;
    }

    private void deleteSite(String site) {
        for (SiteInfo siteInfo : sitesInfo()) {
            if (siteInfo.getUrl().equals(site)) {
                //deletePages(siteInfo.getId()); //TODO: вернуть, как пропишу удаление PAGE
                siteRepository.deleteById(siteInfo.getId());
            }
        }
    }

    private void deletePages(int siteId) {
        for (PageInfo pageInfo : pageInfo()) {
            if (pageInfo.getSiteId().getId() == siteId) {
                pageRepository.deleteById(pageInfo.getId());
            }
        }
        pageRepository.deleteById(siteId);
    }

    private void parse(Site site) {
        SiteDTO siteDTO = new SiteDTO();
        siteTableData(siteDTO, site);
        ParseHtmlPage parse = new ParseHtmlPage(site.getUrl());
        pageTableData(siteDTO, parse.invoke());
    }

    private void siteTableData(SiteDTO siteDTO, Site site) {
        siteDTO.setUrl(site.getUrl());
        siteDTO.setName(site.getName());
        siteDTO.setStatus(Status.INDEXING);
        siteDTO.setTime(LocalDateTime.now());
        writeSite.write(siteDTO);
    }

    private void pageTableData(SiteDTO siteDTO, Map<String, HashMap<Integer, String>> map) {
        siteDTO.setContent(map);
        writePage.write(siteDTO);
    }

    private List<Site> sites() {
        return sitesList.getSites();
    }

    private List<SiteInfo> sitesInfo() {
        return siteRepository.findAll();
    }

    private List<PageInfo> pageInfo() {
        return pageRepository.findAll();
    }
}