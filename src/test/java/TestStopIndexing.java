import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import searchengine.repository.sql.SiteRepository;
import searchengine.services.stopIndexing.StopIndexingImpl;
import searchengine.services.stopIndexing.StopIndexingService;

import java.util.HashMap;

import static searchengine.services.indexing.core.check.lifethread.LifeThread.addThread;

@SpringBootTest(classes = StopIndexingImpl.class)
public class TestStopIndexing {
    @MockBean
    private SiteRepository siteRepository;
    @Autowired
    private StopIndexingService stopIndexing;
    @Test
    @DisplayName("Остановка индексации Fail")
    public void stopIndexingFail(){
        HashMap<String, Object> response = stopIndexing.stopIndexing();
        Assertions.assertArrayEquals(response.entrySet().toArray(), expectFalse().entrySet().toArray());
    }
    @Test
    @DisplayName("Остановка индексации")
    public void stopIndexing(){
        emulatorTask();
        HashMap<String, Object> response = stopIndexing.stopIndexing();
        Assertions.assertArrayEquals(response.entrySet().toArray(), expectTrue().entrySet().toArray());
    }


    private HashMap<String, Object> expectFalse() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("result", false);
        response.put("error", "Индексация не запущена");
        return response;
    }
    private HashMap<String, Object> expectTrue(){
        HashMap<String, Object> response = new HashMap<>();
        response.put("result", true);
        return response;
    }

    private void emulatorTask(){
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(3000000);
                }catch (InterruptedException ignored){
                }
            });
            addThread(thread);
            thread.start();
        }
    }
}
