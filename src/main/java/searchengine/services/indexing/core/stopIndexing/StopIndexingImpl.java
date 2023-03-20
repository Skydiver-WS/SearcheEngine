package searchengine.services.indexing.core.stopIndexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.status.Status;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.SiteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class StopIndexingImpl implements StopIndexingService {
    @Autowired
    private SiteRepository repository;
    private static final List<Thread> threadList = Collections.synchronizedList(new ArrayList<>());

    @Override
    public HashMap<String, Object> stopIndexing() {
        HashMap<String, Object> response = new HashMap<>();
        if (threadList.size() > 0) {
            threadList.forEach(Thread::interrupt);
            threadList.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            setStatusFailed();
            threadList.clear();
            response.put("result", true);
            return response;
        }
        response.put("result", false);
        response.put("error", "Индексация не запущена");
        return response;
    }

    private void setStatusFailed() {
        List<SiteInfo> list = repository.findAll();
        for (SiteInfo site : list) {
            if (site.getStatus().equals(Status.INDEXING)) {
                site.setStatus(Status.FAILED);
                site.setStatusTime(LocalDateTime.now());
                site.setLastError("Индексация остановлена пользователем");
                repository.save(site);
            }
        }
    }

    public static void addThread (Thread thread){
        threadList.add(thread);
    }

    public static void removeThread (Thread thread){
        threadList.remove(thread);
    }
}
