package searchengine.services.lemma;

import lombok.SneakyThrows;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.Morphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class LemmaAnalyze {
    @SneakyThrows
    public Map<String, Integer> lemma(String text){
        Map<String , Integer> lemma = new TreeMap<>();
        text = text.replaceAll("[,:\\-.!?\"();\\[\\]{}/& \\d]", " ").toLowerCase().trim();
        String[] splitText = text.split("\\s+");
        for (String t: splitText) {
            LuceneMorphology morphology = new RussianLuceneMorphology();
            t = morphology.getNormalForms(t).get(0);
            lemma.put(t, 1);
        }
        return lemma;
    }
}
