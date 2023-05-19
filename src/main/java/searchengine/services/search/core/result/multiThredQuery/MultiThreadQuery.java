package searchengine.services.search.core.result.multiThredQuery;

import lombok.AllArgsConstructor;
import searchengine.model.SQL.PageInfo;
import searchengine.repository.SQL.PageRepository;

import java.util.List;
import java.util.concurrent.RecursiveTask;
@AllArgsConstructor
public class MultiThreadQuery extends RecursiveTask<PageInfo> {

    private final PageRepository pageRepository;
    //private List<Integer> listId;
    private Integer id;

    @Override
    protected PageInfo compute() {
        return pageRepository.findById(id).orElse(null);
    }
}
