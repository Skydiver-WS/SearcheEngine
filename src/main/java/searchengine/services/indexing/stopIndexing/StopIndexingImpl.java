package searchengine.services.indexing.stopIndexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.status.Status;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.indexing.IndexingImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class StopIndexingImpl implements StopIndexingService {
    @Autowired
    private SiteRepository repository;

    @Override
    public boolean check() {
//        if (IndexingImpl.isAliveThread()) {
//            Logger.getLogger(StopIndexingImpl.class.getName()).info(Thread.currentThread().getName() + " stop.");
           setStatusFailed();
//            return true;
//        }
        return true;
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
