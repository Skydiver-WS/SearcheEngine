package searchengine.services.writeDataDB.SQL.pageTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.repository.SQL.PageRepository;

import java.util.ArrayList;


@Service

public class WritePageTableImpl implements WritePageTableService {
    @Autowired
    private PageRepository pageRepository;

    @Override
    public void write(SiteDTO siteDTO) {
        ArrayList<PageInfo> list = new ArrayList<>();
        for (PageDTO pageDTO : siteDTO.getPageDTOList()) {
            if(pageDTO.getId() != null){
                updatePage(pageDTO);
                continue;
            }
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPath(pageDTO.getUrl());
            pageInfo.setContent(pageDTO.getContent());
            pageInfo.setCode(pageDTO.getCodeResponse());
            pageInfo.setSiteId(siteDTO.getSiteInfo());
            list.add(pageInfo);
        }
        synchronized (pageRepository) {
            try {
                pageRepository.saveAll(list);
            }catch (Exception ex){
                //TODO: прописать здесь логгирование
            }
        }
    }

    @Override
    public void updatePage(PageDTO pageDTO) {
        pageRepository.updatePage(pageDTO.getUrl(), pageDTO.getContent());
    }
}
