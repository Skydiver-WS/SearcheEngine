package searchengine.services.indexing.core.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.config.status.Status;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.services.indexing.core.check.duplicateUrl.CheckDuplicateRef;
import searchengine.services.indexing.core.handler.WriteDbService;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;

@Component
public class Parse implements ParseService {
    @Autowired
    private WriteDbService writeSqlDbService;

    @Override
    public SiteDTO getListPageDto(SiteDTO siteDTO) {
        ParseHtmlPage parse = new ParseHtmlPage(siteDTO.getSiteInfo().getUrl());
        List<PageDTO> list = parse.invoke().stream().toList();
        CheckDuplicateRef duplicateRef = new CheckDuplicateRef(list);
        siteDTO.setPageDTOList(duplicateRef.getList());
        return siteDTO;
    }

    @Override
    public PageDTO parsePage(PageDTO pageDTO) {
        try {
            String site = pageDTO.getSiteInfo().getUrl();
            String path = pageDTO.getUrl();
            String url = site + (path.equals(site) ? "" : path);
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows; U; WindowsNT" +
                            "5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com").get(); // TODO: вынести в конфигурацию
            pageDTO.setCodeResponse(doc.connection().response().statusCode());
            pageDTO.setContent(doc.html());

        } catch (IOException ex) {
            writeSqlDbService.setStatus(pageDTO.getSiteInfo().getUrl(), Status.FAILED, pageDTO.getUrl() + " - " + ex.getMessage());
        }
        return pageDTO;
    }
}
