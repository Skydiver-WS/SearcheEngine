package searchengine.services.search.core.pages;

import searchengine.dto.search.FrequencyLemmaDTO;

import java.util.List;

public interface SearchPagesService {
    List<FrequencyLemmaDTO> searchPages(FrequencyLemmaDTO[] list);
    List<FrequencyLemmaDTO> searchPages(FrequencyLemmaDTO[] list, String path);
}
