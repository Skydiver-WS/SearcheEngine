package searchengine.dto.statistics;

import lombok.Getter;
import lombok.Setter;
import searchengine.model.sql.PageInfo;
@Getter
@Setter
public class LemmaStatisticsDTO {
    private PageInfo pageInfo;
    private int countLemmas;
}
