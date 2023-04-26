package searchengine.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchObjectDTO {
    private int indexId;
    private int lemmaId;
    private String lemma;
    private int frequency;
    private float rank;
    private int pageId;
    private int siteId;
}
