package searchengine.services.indexing.lemmaAnalyze.injectText;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.PageDTO;
import searchengine.services.indexing.lemmaAnalyze.lemma.LemmaAnalyze;

import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class InjectText extends RecursiveTask<PageDTO> {
    @NonNull
    private PageDTO pageDTO;

    @Override
    @SneakyThrows
    protected PageDTO compute() {
        Logger.getLogger(InjectText.class.getName()).info(Thread.currentThread().getName() + " - start");
        LemmaAnalyze analyze = new LemmaAnalyze(textSplit());
        Map<String, Integer> lemmas = analyze.runAnalyze();
        List<LemmaDTO> listLemmas = new ArrayList<>();
        for (String lemma:lemmas.keySet()) {
            LemmaDTO lemmaDTO = new LemmaDTO();
            lemmaDTO.setLemma(lemma);
            lemmaDTO.setCount(lemmas.get(lemma));
            listLemmas.add(lemmaDTO);
        }
        pageDTO.setLemmas(listLemmas);
        return pageDTO;
    }

    private String[] textSplit() {
        return clearText().split("\\s+");
    }

    private String clearText() {
        String content = Jsoup.clean(pageDTO.getContent(), Safelist.simpleText());
        content = content.replaceAll("[,:\\-.!?\"();\\[\\]{}/& \\d<>|«»©◄]", " ").toLowerCase().trim();
        return content;
    }
}
