package searchengine.dto.search;

import lombok.Data;

import java.util.List;

@Data
public class ResponseSearchDTO {
    private boolean result;
    private String error;
    private int count;
    private List<ResultDTO> data;
}
