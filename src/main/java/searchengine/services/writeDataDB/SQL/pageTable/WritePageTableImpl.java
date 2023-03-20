package searchengine.services.writeDataDB.SQL.pageTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;


@Service
@Transactional
public class WritePageTableImpl implements WritePageTableService {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SiteRepository siteRepository;

    @Override
    public void write(SiteDTO siteDTO) {
        ArrayList<PageInfo> list = new ArrayList<>();
        for (PageDTO pageDTO:siteDTO.getPageDTOList()) {
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPath(pageDTO.getUrl());
            pageInfo.setContent(pageDTO.getContent());
            pageInfo.setCode(pageDTO.getCodeResponse());
            pageInfo.setSiteId(siteDTO.getSiteInfo());
            list.add(pageInfo);
        }
        synchronized (pageRepository){
            pageRepository.saveAll(list);
        }
    }
}
