package searchengine.services.indexing.stopIndexing;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StopIndexing implements StopIndexingService {
  @Override
  public boolean stop(ArrayList<Thread> threadList) {
    if (threadList.size() > 0) {
      for (Thread thread : threadList) {
        thread.interrupt();
      }
      return true;
    }
    return false;
  }
}
