package searchengine.config.site;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Site {
    private String url;
    private String name;

    public void setUrl(String url) {
        this.url = url.trim().replace("www.", "").replaceFirst("/$", "");
    }
}
