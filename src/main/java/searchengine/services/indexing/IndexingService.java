package searchengine.services.indexing;

import java.util.HashMap;

public interface IndexingService {
 HashMap<String, Object> startIndexing();
 HashMap<String, Object> stopIndexing();
}
