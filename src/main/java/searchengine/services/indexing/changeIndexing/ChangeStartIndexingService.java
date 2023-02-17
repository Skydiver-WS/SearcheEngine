package searchengine.services.indexing.changeIndexing;

import java.util.ArrayList;

public interface ChangeStartIndexingService {
    boolean change(ArrayList<Thread> threadList);
}
