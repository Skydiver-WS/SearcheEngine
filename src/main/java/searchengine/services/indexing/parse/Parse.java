package searchengine.services.indexing.parse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.services.indexing.checkDuplicateUrl.CheckDuplicateRef;

import java.util.List;

@Component
public class Parse implements ParseService {

    @Override
    public SiteDTO getListPageDto(SiteDTO siteDTO) {
        ParseHtmlPage parse = new ParseHtmlPage(siteDTO.getSiteInfo().getUrl());
        List<PageDTO> list = parse.invoke().stream().toList();
        CheckDuplicateRef duplicateRef = new CheckDuplicateRef(list);
        siteDTO.setPageDTOList(duplicateRef.getList());
        return siteDTO;
    }
}
