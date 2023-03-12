package searchengine.services.indexing.stopIndexing;

import java.util.ArrayList;
import java.util.List;

public interface StopIndexingService {
    boolean stop(List<Thread> threadList);
}
