package searchengine.services.indexing.core.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.config.jsoup.JsoupConf;
import searchengine.config.status.Status;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.services.indexing.core.check.duplicateUrl.CheckDuplicateRef;
import searchengine.services.indexing.core.handler.WriteDbService;

import java.io.IOException;
import java.util.List;

@Component
public class Parse implements ParseService {
    @Autowired
    private WriteDbService writeSqlDbService;
    @Autowired
    private JsoupConf jsoupConf;

    @Override
    public void getListPageDto(SiteDTO siteDTO) {

        ParseHtmlPage parse = new ParseHtmlPage(siteDTO.getSiteInfo().getUrl(), jsoupConf(jsoupConf));
        List<PageDTO> list = parse.invoke().stream().toList();
        CheckDuplicateRef duplicateRef = new CheckDuplicateRef(list);
        siteDTO.setPageDTOList(duplicateRef.getList());
    }

    @Override
    public PageDTO parsePage(PageDTO pageDTO) {
        try {
            String[] conf = jsoupConf(jsoupConf);
            String url = pageDTO.getUrl();
            Document doc = Jsoup.connect(url).userAgent(conf[0])
                    .referrer(conf[1]).get(); // TODO: вынести в конфигурацию
            pageDTO.setCodeResponse(doc.connection().response().statusCode());
            pageDTO.setContent(doc.html());
            return pageDTO;
        } catch (IOException ex) {
            writeSqlDbService.setStatus(pageDTO.getSiteInfo().getUrl(), Status.FAILED, pageDTO.getUrl() + " - " + ex.getMessage());
        }
        return null;
    }

    private String[] jsoupConf (JsoupConf jsoupConf){
        return new String[]{jsoupConf.getUserAgent(), jsoupConf.getReferrer()};
    }
}
