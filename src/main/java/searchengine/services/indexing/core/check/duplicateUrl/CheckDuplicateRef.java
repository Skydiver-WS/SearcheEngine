package searchengine.services.indexing.core.check.duplicateUrl;

import lombok.Getter;
import searchengine.dto.sites.PageDTO;

import java.util.ArrayList;
import java.util.List;

public class CheckDuplicateRef {
    @Getter
    private List<PageDTO> list;

    public CheckDuplicateRef(List<PageDTO> list) {
        removeDuplicate(list);
    }

    private void removeDuplicate(List<PageDTO> list) {
        List<PageDTO> newList = new ArrayList<>();
        for (PageDTO page: list) {
            if (checkNewList(newList, page.getUrl())) {
                newList.add(page);
            }
        }
        this.list = newList;
    }

    private boolean checkNewList(List<PageDTO> newList, String url) {
        for (PageDTO page: newList) {
            if(page.getUrl().equals(url)){
                return false;
            }
        }
        return true;
    }
}
