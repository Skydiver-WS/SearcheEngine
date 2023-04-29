package searchengine.config.site;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
public class Site {
    private String url;
    private String name;

    public void setUrl(String url) {
        this.url = url.trim().replace("www.", "").replaceFirst("/$", "");
    }
}
