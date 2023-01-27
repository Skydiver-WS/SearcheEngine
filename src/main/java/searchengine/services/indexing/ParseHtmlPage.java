package searchengine.services.indexing;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class ParseHtmlPage extends RecursiveTask<Set<String>> {
    private final HashSet<String> finalWebStructure = new HashSet<>();
    @NonNull
    private String url;

    @SneakyThrows
    @Override
    protected Set<String> compute() {
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows; U; WindowsNT" +
                        "5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com").get(); // TODO: вынести в конфигурацию
        List<String> listAllRef = doc.select("a").eachAttr("abs:href");
        TreeSet<String> checkRef = filterSite(listAllRef);
        ArrayList<ParseHtmlPage> pages = new ArrayList<>();
        finalWebStructure.add(url);
        for (String url:checkRef) {
            if(finalWebStructure.contains(url)){
                Thread.sleep(500);
                ParseHtmlPage htmlPage = new ParseHtmlPage(url);
                htmlPage.fork();
                pages.add(htmlPage);
            }
        }
        for (ParseHtmlPage page:pages) {
            try {
                page.join();
            }catch (Exception ex){
                System.out.println("Не поддерживаемый контент");
            }
        }
        return finalWebStructure;
    }

    private TreeSet<String> filterSite(List<String> list) {
        TreeSet<String> filterList = new TreeSet<>();
        Pattern pattern = Pattern.compile(url);
        for (String url : list) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                filterList.add(url);
            }
        }
        return filterList;
    }
}
