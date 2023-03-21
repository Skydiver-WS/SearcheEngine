import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import searchengine.config.site.SitesList;
import searchengine.services.indexing.singleIndexing.IndexPageImpl;
import searchengine.services.indexing.singleIndexing.IndexPageService;

import java.util.HashMap;

@SpringBootTest(classes = IndexPageImpl.class)
public class TestIndexPage {
    @Autowired
    private IndexPageService indexPageService;

    @Test
    @DisplayName("Индексация страницы прошла успешно")
    public void indexingPageTrue(){
        String url = "https://skillbox.ru/";
        Assertions.assertArrayEquals(indexPageService.indexPage(url).entrySet().toArray(),
                actualTrue().entrySet().toArray());
    }
    @Test
    @DisplayName("Страница находится за пределами конфигурации")
    public void indexingPageFalse(){
        String url = "testUrl";
        Assertions.assertArrayEquals(indexPageService.indexPage(url).entrySet().toArray(),
                actualFalse().entrySet().toArray());
    }
    private HashMap<String, Object> actualTrue(){
        HashMap<String, Object> response = new HashMap<>();
        response.put("result", true);
        return response;
    }
    private HashMap<String, Object> actualFalse(){
        HashMap<String, Object> response = new HashMap<>();
        response.put("result", false);
        response.put("error", "Данная страница находится за пределами сайтов," +
                "указанных в конфигурационном файле");
        return response;
    }
}
