package searchengine.services.indexing.core.lemma.injectText;


import lombok.*;
import org.apache.lucene.morphology.LuceneMorphology;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import searchengine.dto.sites.LemmaDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.services.indexing.core.lemma.analyzer.LemmaAnalyze;

import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

/**
 *   Данный класс  на вход получает объект {@link PageInfo} или запрос query
 * и предназначен для получения лемм и их количества.
 */

public class InjectText extends RecursiveTask<TreeMap<Integer, List<LemmaDTO>>> {
    private PageInfo pageInfo;
    private final LuceneMorphology[] morphologies;
    private String query;

    public InjectText(PageInfo pageInfo, LuceneMorphology[] morphologies) {
        this.pageInfo = pageInfo;
        this.morphologies = morphologies;
    }

    public InjectText(String query, LuceneMorphology[] morphologies) {
        this.morphologies = morphologies;
        this.query = query;
    }

    /**
     * Данный метод запускает анализатор {@link LemmaAnalyze} и список объектов {@link LemmaDTO}
     * сгрупированным по id таблицы {@link PageInfo}
     */
    @Override
    @SneakyThrows
    protected TreeMap<Integer, List<LemmaDTO>> compute() {
        Logger.getLogger(InjectText.class.getName()).info(Thread.currentThread().getName() + " - start");
        LemmaAnalyze analyze = new LemmaAnalyze(textSplit(), morphologies);
        Map<String, Integer> lemmas = analyze.runAnalyze();
        List<LemmaDTO> listLemmas = new ArrayList<>();
        TreeMap<Integer, List<LemmaDTO>> map = new TreeMap<>();
        for (String lemma : lemmas.keySet()) {
            LemmaDTO lemmaDTO = new LemmaDTO();
            lemmaDTO.setLemma(lemma);
            lemmaDTO.setCount(lemmas.get(lemma));
            listLemmas.add(lemmaDTO);
        }
        map.put(pageInfo.getId(), listLemmas);
        return map;
    }

    public Map<String, Integer> getLemmas() {
        LemmaAnalyze analyze = new LemmaAnalyze(textSplit(), morphologies);
        return analyze.runAnalyze();
    }

    private String[] textSplit() {
        return clearText().split("[\\s-,.:]");
    }

    private String clearText() {
        String text = pageInfo != null ? pageInfo.getContent() : query;
        String content = Jsoup.clean(text, Safelist.simpleText());
        content = content.replaceAll("[,:\\-.!?\"();\\[\\]{}/& \\d<>|«»©◄]", " ").toLowerCase().trim();
        return content;
    }
}
