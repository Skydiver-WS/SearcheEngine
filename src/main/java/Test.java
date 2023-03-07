import lombok.SneakyThrows;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import searchengine.services.indexing.lemmaAnalyze.lemma.LemmaAnalyze;

public class Test {
    @SneakyThrows
    public static void main(String[] args) {
//        ArrayList<ForkJoinTask<Integer>> test = new ArrayList<>();
//        InjectText injectText;
//        for (int i = 0; i < 100; i++) {
//            injectText = new InjectText();
//            //injectText.setI(i);
//            test.add(injectText.fork());
//        }
//        for (ForkJoinTask<Integer> test2: test){
//            System.out.println(test2.join());
//        }
//        for (ForkJoinTask<Integer> test2: test) {
//            System.out.println(test2.get());
//        }
        LuceneMorphology luceneMorphology = new EnglishLuceneMorphology();
        System.out.println(luceneMorphology.getMorphInfo("b"));

    }
}
