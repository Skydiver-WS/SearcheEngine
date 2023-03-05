package searchengine.services.indexing.lemmaAnalyze;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.services.indexing.lemmaAnalyze.injectText.InjectText;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.logging.Logger;

@Service
public class LemmaImpl implements LemmaService {
    private ArrayList<Map<String, Integer>> listLemmas = new ArrayList<>();
    @SneakyThrows
    @Override
    public void lemma(SiteDTO siteDTO) {
        ArrayList<ForkJoinTask<Map<String, Integer>>> listInjectClass = new ArrayList<>();
        InjectText injectText;
        long start = System.currentTimeMillis();
        for (PageDTO page : siteDTO.getPageDTO()) {
                injectText = new InjectText();
                injectText.setContent(page.getContent());
                listInjectClass.add(injectText.fork());
        }
        for (ForkJoinTask<Map<String, Integer>> text:listInjectClass) {
            listLemmas.add(text.join());
            Logger.getLogger(InjectText.class.getName()).info(Thread.currentThread().getName() + " - finish");
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
