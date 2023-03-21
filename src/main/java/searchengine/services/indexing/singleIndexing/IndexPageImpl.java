package searchengine.services.indexing.singleIndexing;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IndexPageImpl implements IndexPageService {
    @Override
    public HashMap<String, Object> indexPage(String url) {
        HashMap<String, Object> response = new HashMap<>();
        if (true) {
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
