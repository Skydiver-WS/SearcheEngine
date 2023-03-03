package searchengine.services.indexing.lemmaAnalyze.injectText;


import lombok.Setter;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import searchengine.services.indexing.lemmaAnalyze.lemma.LemmaAnalyze;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

public class InjectText extends RecursiveTask<Map<String, Integer>> {
    @Setter
    String content;
    @Override
    @SneakyThrows
    protected Map<String, Integer> compute() {
        Logger.getLogger(InjectText.class.getName()).info(Thread.currentThread().getName() + " - start");
        LemmaAnalyze analyze = new LemmaAnalyze(textCompression());
        return analyze.runAnalyze();
    }

    private Map<String, Integer> textCompression() {
        String clear = clearText();
        Map<String, Integer> list = new TreeMap<>();
        String[] splitText = clear.split("\\s+");
        for (String t : splitText) {
            if (t.length() > 1) {
                list.put(t.trim(), countLemmas(list, t));
            }
        }
        return list;
    }

    private String clearText(){
        content = Jsoup.clean(content, Safelist.simpleText());
        content = content.replaceAll("[,:\\-.!?\"();\\[\\]{}/& \\d<>|«»©]", " ").toLowerCase().trim();
        return content;
    }

    private int countLemmas(Map<String, Integer> list, String text) {
        int count = 1;
        if (list.containsKey(text)) {
            count = list.get(text) + 1;
        }
        return count;
    }
}
