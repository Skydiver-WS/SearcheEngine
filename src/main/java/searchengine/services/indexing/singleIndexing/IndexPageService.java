package searchengine.services.indexing.singleIndexing;

import java.util.HashMap;

public interface IndexPageService {
    HashMap<String, Object> indexPage(String url);
}
