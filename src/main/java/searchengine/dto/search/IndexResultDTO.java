package searchengine.dto.search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class IndexResultDTO {
    private int accuracy;
    private List<Integer> listIndex;
}
