package searchengine.dto.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDTO {
    String site;
    String siteName;
    String uri;
    String title;
    String snippet;
    double relevance;
}
