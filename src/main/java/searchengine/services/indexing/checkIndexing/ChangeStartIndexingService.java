package searchengine.services.indexing.checkIndexing;

import java.util.ArrayList;

public interface ChangeStartIndexingService {
    boolean change(ArrayList<Thread> threadList);
}
