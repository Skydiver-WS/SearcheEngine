package searchengine.services.indexing.core.lemmaAnalyze.injectText;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import searchengine.dto.sites.LemmaDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.services.indexing.core.lemmaAnalyze.lemma.LemmaAnalyze;

import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class InjectText extends RecursiveTask< TreeMap<Integer, List<LemmaDTO>>> {
    @NonNull
    private PageInfo pageInfo;

    @Override
    @SneakyThrows
    protected TreeMap<Integer, List<LemmaDTO>> compute() {
        Logger.getLogger(InjectText.class.getName()).info(Thread.currentThread().getName() + " - start");
        LemmaAnalyze analyze = new LemmaAnalyze(textSplit());
        Map<String, Integer> lemmas = analyze.runAnalyze();
        List<LemmaDTO> listLemmas = new ArrayList<>();
        TreeMap<Integer, List<LemmaDTO>> map = new TreeMap<>();
        for (String lemma:lemmas.keySet()) {
            LemmaDTO lemmaDTO = new LemmaDTO();
            lemmaDTO.setLemma(lemma);
            lemmaDTO.setCount(lemmas.get(lemma));
            listLemmas.add(lemmaDTO);
        }
        map.put(pageInfo.getId(), listLemmas);
        return map;
    }

    private String[] textSplit() {
        return clearText().split("\\s+");
    }

    private String clearText() {
        String content = Jsoup.clean(pageInfo.getContent(), Safelist.simpleText());
        content = content.replaceAll("[,:\\-.!?\"();\\[\\]{}/& \\d<>|«»©◄]", " ").toLowerCase().trim();
        return content;
    }
}
