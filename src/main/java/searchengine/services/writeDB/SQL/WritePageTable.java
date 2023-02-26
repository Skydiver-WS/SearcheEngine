package searchengine.services.writeDB.SQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.indexing.IndexingImpl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.TreeSet;

@Service
public class WritePageTable implements WritePageTableService {
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SiteInfo siteInfo;

    @Override
    public synchronized SiteDTO write(SiteDTO siteDTO) {
        ArrayList <PageInfo> list = new ArrayList<>();
        for (int i = 0; i < siteDTO.getPagesInfo().size(); i++) {
            PageDTO pageDTO = pageDTO(siteDTO, i);
            PageInfo pageInfo = new PageInfo();
            pageInfo.setId(0);
            pageInfo.setSiteId(site(siteDTO));
            pageInfo.setPath(pageDTO.getUrl());
            pageInfo.setCode(pageDTO.getCodeResponse());
            pageInfo.setContent(pageDTO.getContent());
            list.add(pageInfo);
        }
        if(IndexingImpl.getListThread().size() > 0){
            pageRepository.saveAllAndFlush(list);
        }
        return siteDTO;
    }

    private SiteInfo site(SiteDTO siteDTO) {
        Optional<SiteInfo> site = siteRepository.findById(siteDTO.getIdSite());
        return site.orElse(null);
    }
    private PageDTO pageDTO(SiteDTO siteDTO, int i){
        return siteDTO.getPagesInfo().get(i);
    }
}
