package searchengine.services.indexing.lemmaAnalyze.lemma;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.springframework.stereotype.Service;
import searchengine.services.writeDB.SQL.WriteLemmaTable;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LemmaAnalyze {
    @NonNull
    private Map<String, Integer> splitText;
    private static LuceneMorphology morphologyRus;
    private static LuceneMorphology morphologyEng;


    public Map<String, Integer> runAnalyze() {
        Map<String, Integer> lemma = new TreeMap<>();
        Pattern pattern = Pattern.compile("СОЮЗ|МЕЖД|ПРЕДЛ|ARTICLE|CONJ|VBE|PN|PN_ADJ|PREP");
        init();
        for (String key : splitText.keySet()) {
            try {
                Logger.getLogger(WriteLemmaTable.class.getName()).info("text  - " + key);
                String newForm = morphologyRus.getMorphInfo(key).get(0);
                Logger.getLogger(WriteLemmaTable.class.getName()).info("Russian lemma find - " + newForm);
                Matcher matcher = pattern.matcher(newForm);
                if (!matcher.find()) {
                    lemma.put(finalText(newForm), splitText.get(key));
                }
            } catch (Exception ex) {
                String newForm = lemmaEnglish(key, pattern);
                if(newForm != null){
                    lemma.put(newForm, splitText.get(key));
                }
            }
        }
        return lemma;
    }


    private String lemmaEnglish(String key, Pattern pattern) {
        try {
            Logger.getLogger(WriteLemmaTable.class.getName()).info("text - " + key);
            String newForm = morphologyEng.getMorphInfo(key).get(0);
            Logger.getLogger(WriteLemmaTable.class.getName()).info("English lemma find - " + newForm);
            Matcher matcher = pattern.matcher(newForm);
            if (!matcher.find()) {
                return key;
            }
        } catch (Exception ex) {
            Logger.getLogger(LemmaAnalyze.class.getName()).warning(ex.getMessage());
        }
        return null;
    }

    private String finalText(String text) {
        return text.replaceAll("\\|.*", "");
    }
    @SneakyThrows
    private static void init(){
        morphologyRus = new RussianLuceneMorphology();
        morphologyEng = new EnglishLuceneMorphology();
    }
}
