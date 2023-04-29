package searchengine.dto.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FrequencyLemmaDTO {
    private double repeat;
    private String lemma;
    private int pageId;
    private float rank;
}
