package searchengine.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchObjectDTO {
    private int lemmaId;
    private String lemma;
    private int frequency;
    private int indexId;
    private float rank;
    private int pageId;
}
