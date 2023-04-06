package searchengine.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@AllArgsConstructor
public class PageStatisticsDTO {
    private Integer siteId;
    private Long countPage;
}
