package searchengine.services.indexing.stopIndexing;

import java.util.ArrayList;

public interface StopIndexingService {
    boolean stop(ArrayList<Thread> threadList);
}
