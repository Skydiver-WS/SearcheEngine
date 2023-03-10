package searchengine.repository.SQL;

import searchengine.model.SQL.PageInfo;

import java.util.List;

public interface CustomPageRepository {
    List<PageInfo> insertNewPages (List<PageInfo> pages);
}
