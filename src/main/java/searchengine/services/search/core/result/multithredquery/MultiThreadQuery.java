package searchengine.services.search.core.result.multithredquery;

import lombok.AllArgsConstructor;
import searchengine.model.sql.PageInfo;
import searchengine.repository.sql.PageRepository;

import java.util.concurrent.RecursiveTask;
@AllArgsConstructor
public class MultiThreadQuery extends RecursiveTask<PageInfo> {

    private final PageRepository pageRepository;
    private Integer id;

    @Override
    protected PageInfo compute() {
        return pageRepository.findById(id).orElse(null);
    }
}
