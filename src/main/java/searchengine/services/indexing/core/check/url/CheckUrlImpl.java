package searchengine.services.indexing.core.check.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.config.site.SitesList;

import java.util.regex.Pattern;
@Component
public class CheckUrlImpl implements CheckUrlService {
    @Autowired
    private SitesList sitesList;

    @Override
    public boolean check(String url) {
        return sitesList.getSites().stream().anyMatch(site -> Pattern.compile(site.getUrl()).matcher(url).find());
    }
}
