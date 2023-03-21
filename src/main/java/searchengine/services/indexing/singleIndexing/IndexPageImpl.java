package searchengine.services.indexing.singleIndexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.services.indexing.core.check.url.CheckUrlService;

import java.util.HashMap;

@Service
public class IndexPageImpl implements IndexPageService {
    @Autowired
    private CheckUrlService checkUrl;
    @Override
    public HashMap<String, Object> indexPage(String url) {
        HashMap<String, Object> response = new HashMap<>();
        if (checkUrl.check(url)) {
            response.put("result", true);
            return response;
        } else {
            response.put("result", false);
            response.put("error", "Данная страница находится за пределами сайтов, " +
                    "указанных в конфигурационном файле.");
            return response;
        }


    }
}
