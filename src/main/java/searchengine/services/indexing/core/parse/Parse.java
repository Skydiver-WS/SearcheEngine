package searchengine.services.indexing.core.parse;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.services.indexing.core.check.duplicateUrl.CheckDuplicateRef;

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

    @Override
    @SneakyThrows
    public PageDTO parsePage(PageDTO pageDTO) {
        Document doc = Jsoup.connect(pageDTO.getUrl()).userAgent("Mozilla/5.0 (Windows; U; WindowsNT" +
                        "5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com").get(); // TODO: вынести в конфигурацию
        pageDTO.setCodeResponse(doc.connection().response().statusCode());
        pageDTO.setContent(doc.html());
        return pageDTO;
    }
}
