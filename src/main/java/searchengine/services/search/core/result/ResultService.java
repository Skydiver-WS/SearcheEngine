package searchengine.services.search.core.result;

import searchengine.dto.search.RelevanceDTO;
import searchengine.dto.search.ResultDTO;

import java.util.List;

public interface ResultService {
    ResultDTO[] getResult(RelevanceDTO[] dto);
}
