package searchengine.services.search.core.relative;

import org.springframework.stereotype.Service;
import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.dto.search.RelevanceDTO;

import java.util.*;

@Service
public class DefiniteRelevanceImpl implements DefiniteRelevanceService {
    @Override
    public RelevanceDTO[] getList(List<FrequencyLemmaDTO> list) {
        List<RelevanceDTO> listDTO = new ArrayList<>();
        HashSet<Integer> filter = new HashSet<>();
        for (FrequencyLemmaDTO dto : list){
            RelevanceDTO obj = new RelevanceDTO();
            obj.setFrequencyLemmaDTOList(list.stream()
                    .filter(d -> d.getPageId() == dto.getPageId())
                    .filter(d -> !filter.contains(d.getPageId()))
                    .toList());
            int pageId = dto.getPageId();
            if (!obj.getFrequencyLemmaDTOList().isEmpty()){
                obj.setPageId(pageId);
                listDTO.add(obj);
            }
            filter.add(pageId);
        }
        RelevanceDTO[] obj = getRelevance(listDTO).toArray(new RelevanceDTO[0]);
        Arrays.sort(obj, Comparator.comparingDouble(RelevanceDTO::getRelRelevance).reversed());
        return obj;
    }
    private List<RelevanceDTO> getRelevance (List<RelevanceDTO> listDTO) {
        listDTO.forEach(dto -> dto.setAbsRelevance(dto.getFrequencyLemmaDTOList().stream()
                .mapToDouble(FrequencyLemmaDTO::getRank)
                .sum()));
    double absRelMax = listDTO.stream()
            .mapToDouble(RelevanceDTO::getAbsRelevance)
            .max()
            .orElse(Double.NaN);
       listDTO.forEach(dto -> dto.setRelRelevance(dto.getAbsRelevance() / absRelMax));
    return listDTO;
    }
}
