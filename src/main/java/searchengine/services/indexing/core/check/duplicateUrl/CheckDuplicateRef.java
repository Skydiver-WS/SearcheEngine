package searchengine.services.indexing.core.check.duplicateUrl;

import lombok.Getter;
import searchengine.dto.sites.PageDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Класс проверяет наличие повторяющихся url в списке
 */
public class CheckDuplicateRef {
    @Getter
    private List<PageDTO> list = new ArrayList<>();

    public CheckDuplicateRef(List<PageDTO> list) {
        removeDuplicate(list);
    }

    private void removeDuplicate(List<PageDTO> list) {
        Map<String, PageDTO> clearDuplicate = new TreeMap<>();
        for (PageDTO pageDTO : list) {
            clearDuplicate.put(pageDTO.getUrl(), pageDTO);
        }
        for (String key:clearDuplicate.keySet()) {
            this.list.add(clearDuplicate.get(key));
        }
    }
}
