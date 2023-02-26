package searchengine.services.lemma;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LemmaAnalyze {
  public Map<String, Integer> lemma(String text) {
    Map<String, Integer> lemma = new TreeMap<>();
    Map<String, Integer> compress = textCompression(text);
    Pattern pattern = Pattern.compile("СОЮЗ|МЕЖД|ПРЕДЛ|ARTICLE|CONJ|VBE|PN|PN_ADJ|PREP");
    for (String key : compress.keySet()) {
      try {
        LuceneMorphology luceneMorphology = new RussianLuceneMorphology();
        String newForm = luceneMorphology.getMorphInfo(key).get(0);
        Matcher matcher = pattern.matcher(newForm);
        if (!matcher.find()){
          lemma.put(finalText(newForm), compress.get(key));
        }
      }
      catch (Exception ex) {
        lemma.putAll(lemmaEnglish(compress, pattern));
      }
    }
    return lemma;
  }

  private Map<String, Integer> lemmaEnglish(Map<String, Integer> list, Pattern pattern) {
    Map<String, Integer> lemma = new TreeMap<>();
    for (String key : list.keySet()) {
      try {
        LuceneMorphology luceneMorphology = new EnglishLuceneMorphology();
        String newForm = luceneMorphology.getMorphInfo(key).get(0);
        Matcher matcher = pattern.matcher(newForm);
        if(!matcher.find()){
          lemma.put(finalText(newForm), list.get(key));
        }
      }
      catch (Exception ex) {
        //Logger.getLogger(LemmaAnalyze.class.getName()).info(ex.getMessage());
      }
    }
    return lemma;
  }

  private Map<String, Integer> textCompression(String text) {
    Map<String, Integer> list = new TreeMap<>();
    text = text.replaceAll("[,:\\-.!?\"();\\[\\]{}/& \\d<>|]", " ").toLowerCase().trim();
    String[] splitText = text.split("\\s+");
    for (String t : splitText) {
      list.put(t.trim(), changeKey(list, t));
    }
    return list;
  }

  private int changeKey(Map<String, Integer> list, String text) {
    int count = 1;
    if (list.containsKey(text)) {
      count = list.get(text) + 1;
    }
    return count;
  }

  private String finalText(String text){
    return text.replaceAll("\\|.*", "");
  }

}
