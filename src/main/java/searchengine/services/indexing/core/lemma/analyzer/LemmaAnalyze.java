package searchengine.services.indexing.core.lemma.analyzer;

import lombok.AllArgsConstructor;
import org.apache.lucene.morphology.LuceneMorphology;
import org.springframework.stereotype.Service;
import searchengine.services.writedatadb.sql.lemmatable.WriteLemmaTableImpl;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class LemmaAnalyze {
    private String[] splitText;
    private LuceneMorphology[] morphologies;

    public Map<String, Integer> runAnalyze() {
        Map<String, Integer> lemma = new TreeMap<>();
        Pattern pattern = Pattern.compile("СОЮЗ|МЕЖД|ПРЕДЛ|ARTICLE|CONJ|VBE|PN|PN_ADJ|PREP");
        for (String text : splitText) {
            try {
                Logger.getLogger(WriteLemmaTableImpl.class.getName()).info("text  - " + text);
                String newForm = morphologies[0].getMorphInfo(text).get(0);
                Logger.getLogger(WriteLemmaTableImpl.class.getName()).info("Russian lemma find - " + newForm);
                Matcher matcher = pattern.matcher(newForm);
                if (!matcher.find()) {
                    String t = finalText(newForm);
                    lemma.put(t, countLemmas(lemma, t));
                }
            } catch (Exception ex) {
                String newForm = lemmaEnglish(text, pattern);
                if (newForm != null) {
                    lemma.put(newForm, countLemmas(lemma, newForm));
                }
            }
        }
        return lemma;
    }


    private String lemmaEnglish(String text, Pattern pattern) {
        try {
            Logger.getLogger(WriteLemmaTableImpl.class.getName()).info("text - " + text);
            String newForm = morphologies[1].getMorphInfo(text).get(0);
            Logger.getLogger(WriteLemmaTableImpl.class.getName()).info("English lemma find - " + newForm);
            Matcher matcher = pattern.matcher(newForm);
            if (!matcher.find()) {
                return text;
            }
        } catch (Exception ex) {
            Logger.getLogger(LemmaAnalyze.class.getName()).warning(ex.getMessage());
        }
        return null;
    }

    private String finalText(String text) {
        return text.replaceAll("\\|.*", "");
    }

    private int countLemmas(Map<String, Integer> list, String text) {
        int count = 1;
        if (list.containsKey(text)) {
            count = list.get(text) + 1;
        }
        return count;
    }

}
