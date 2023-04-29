package searchengine.services.search.core.pages;

import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.dto.search.SearchObjectDTO;

import java.util.List;

public interface SearchPagesService {
    List<FrequencyLemmaDTO> searchPages(FrequencyLemmaDTO[] list, List<SearchObjectDTO> searchObjectDTOList);
    List<FrequencyLemmaDTO> searchPages(FrequencyLemmaDTO[] list, String path, List<SearchObjectDTO> searchObjectDTOList);
}
