package searchengine.services.stopIndexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.status.Status;
import searchengine.model.sql.SiteInfo;
import searchengine.repository.sql.SiteRepository;
import searchengine.services.indexing.core.check.lifethread.LifeThread;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static searchengine.services.indexing.core.check.lifethread.LifeThread.*;

/**
 * @see StopIndexingImpl - данный класс предназначен, для принудительной остановки индексации.
 */
@Service
public class StopIndexingImpl implements StopIndexingService {
    @Autowired
    private SiteRepository repository;

    /**
     * @see #stopIndexing() - метод останавливает индексацию и возвращает true если остановка выполнена успешно
     *                        или false если индексация не была запущена.
     * Работа метода:
     * @see  LifeThread#isAliveThread() - статичекский метед проверяет, есть ли живые потоки
     * @see LifeThread#getThreadList() - статический метод содержащий информацию об актиынвых потоках.
     * @see LifeThread#clearAllThread() - статический метод производит отчистку списка активных потоков.
     */
    @Override
    public HashMap<String, Object> stopIndexing() {
        HashMap<String, Object> response = new HashMap<>();
        if (isAliveThread()) {
            getThreadList().forEach(Thread::interrupt);
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
