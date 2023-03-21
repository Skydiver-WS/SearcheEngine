package searchengine.services.indexing.core.stopIndexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.status.Status;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.indexing.core.check.lifeThread.LifeThread;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static searchengine.services.indexing.core.check.lifeThread.LifeThread.*;

@Service
public class StopIndexingImpl implements StopIndexingService {
    @Autowired
    private SiteRepository repository;


    @Override
    public HashMap<String, Object> stopIndexing() {
        HashMap<String, Object> response = new HashMap<>();
        if (isAliveThread()) {
            getThreadList().forEach(Thread::interrupt);
            getThreadList().forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            setStatusFailed();
            clearAllThread();
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


}
