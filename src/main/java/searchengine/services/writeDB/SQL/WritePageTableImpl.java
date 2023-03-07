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


import java.util.*;

@Service
public class WritePageTableImpl implements WritePageTableService {
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private WriteLemmaTableService writeLemmaTable;
    @Autowired
    private SiteInfo siteInfo;
    private PageInfo pageInfo;

    @Override
    public SiteDTO write(SiteDTO siteDTO) {
        ArrayList<PageInfo> list = new ArrayList<>();
        for (int i = 0; i < siteDTO.getPageDTO().size(); i++) {
            PageDTO pageDTO = siteDTO.getPageDTO().get(i);
            pageInfo = new PageInfo();
            pageInfo.setId(0);
            pageInfo.setSiteId(getSiteInfo(siteDTO));
            pageInfo.setPath(pageDTO.getUrl());
            pageInfo.setCode(pageDTO.getCodeResponse());
            pageInfo.setContent(pageDTO.getContent());
            list.add(pageInfo);
        }
        if (IndexingImpl.isAliveThread()) {
            siteDTO.setSiteInfo(getSiteInfo(siteDTO));
            synchronized (pageRepository){
               pageRepository.saveAllAndFlush(list);
            }
            writeLemmaTable.write(siteDTO);
        }
        return siteDTO;
    }

    private SiteInfo getSiteInfo(SiteDTO siteDTO) {
        Optional<SiteInfo> site = siteRepository.findById(siteDTO.getIdSite());
        return site.orElse(null);
    }

}
