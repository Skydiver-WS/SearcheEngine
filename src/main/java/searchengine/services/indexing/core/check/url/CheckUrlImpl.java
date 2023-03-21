package searchengine.services.indexing.core.check.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.site.SitesList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class CheckUrlImpl implements CheckUrlService {
    @Autowired
    private SitesList sitesList;

    @Override
    public boolean check(String url) {
        for (Site site : sitesList.getSites()) {
            Pattern pattern = Pattern.compile(site.getUrl());
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }
}
