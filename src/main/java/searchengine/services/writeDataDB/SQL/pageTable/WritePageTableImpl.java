package searchengine.services.writeDataDB.SQL.pageTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.status.Status;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.repository.SQL.PageRepository;
import searchengine.services.indexing.core.handler.WriteDbService;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service

public class WritePageTableImpl implements WritePageTableService {
    @Autowired
    private PageRepository pageRepository;

    @Override
    public void write(SiteDTO siteDTO) {
        ArrayList<PageInfo> list = new ArrayList<>();
        for (PageDTO pageDTO : siteDTO.getPageDTOList()) {
            if (pageDTO.getId() != null) {
                updatePage(pageDTO);
                continue;
            }
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPath(convertUrl(pageDTO.getUrl()));
            pageInfo.setContent(pageDTO.getContent());
            pageInfo.setCode(pageDTO.getCodeResponse());
            pageInfo.setSiteId(siteDTO.getSiteInfo());
            list.add(pageInfo);
        }
        synchronized (pageRepository) {
            pageRepository.saveAll(list);
        }
    }

    @Override
    public void updatePage(PageDTO pageDTO) {
        pageRepository.updatePage(convertUrl(pageDTO.getUrl()), pageDTO.getContent());
    }

    private String convertUrl(String url) {
        Pattern pattern = Pattern.compile("^.+://.+?/");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            url = "/" + url.replaceFirst(pattern.pattern(), "");
        }
        return url;
    }
}
