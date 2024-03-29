package searchengine.dto.search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RelevanceDTO {
    private double absRelevance;
    private double relRelevance;
    private int pageId;
    private List<FrequencyLemmaDTO> listFrequencyLemmaDTOList;
}
