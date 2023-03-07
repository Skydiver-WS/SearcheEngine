package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;
import searchengine.model.SQL.PageInfo;

@Getter
@Setter
public class LemmaDTO {
    private String lemma;
    private int count;
}
