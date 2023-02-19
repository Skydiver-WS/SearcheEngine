package searchengine.services.indexing.parse;

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
public class ParseHtmlPage extends RecursiveTask<Map<String, HashMap<Integer, String>>> {
    private final TreeMap<String, HashMap<Integer, String>> finalWebStructure = new TreeMap<>();
    private final HashMap<Integer, String> content = new HashMap<>();

    @NonNull
    private String url;

    @SneakyThrows
    @Override
    protected Map<String, HashMap<Integer, String>> compute() {
        url = insertUrl(url);
        if (!finalWebStructure.containsKey(url)) {

            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows; U; WindowsNT" +
                            "5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com").get(); // TODO: вынести в конфигурацию
            content.put(doc.connection().response().statusCode(), doc.html());
            finalWebStructure.put(url, content);
            List<String> listAllRef = doc.select("a").eachAttr("abs:href");
            TreeSet<String> checkRef = filterSite(listAllRef);
            ArrayList<ParseHtmlPage> pages = new ArrayList<>();
            fork(pages, checkRef);
            join(pages);
        }
        return finalWebStructure;
    }

    @SneakyThrows
    private void fork(ArrayList<ParseHtmlPage> pages, Set<String> checkRef) {
        for (String url : checkRef) {
            Thread.sleep(500);
            ParseHtmlPage htmlPage = new ParseHtmlPage(url);
            htmlPage.fork();
            pages.add(htmlPage);
            //Logger.getLogger(ParseHtmlPage.class.getName()).info(url + " FORK complete");
        }
    }

    private void join(ArrayList<ParseHtmlPage> pages) {
        for (ParseHtmlPage page : pages) {
            try {
                finalWebStructure.putAll(page.join());
                //Logger.getLogger(ParseHtmlPage.class.getName()).info(url + " JOIN complete");
            } catch (Exception ex) {
                //Logger.getLogger(ParseHtmlPage.class.getName()).warning(url + " Не поддерживаемый контент"); //TODO: при ошибке добавить логирование с записью в БД
            }
        }
    }

    private TreeSet<String> filterSite(List<String> list) {
        TreeSet<String> filterList = new TreeSet<>();
        Pattern pattern = Pattern.compile(url);
        for (String ref : list) {
            Matcher matcher = pattern.matcher(ref);
            if (matcher.find() && !finalWebStructure.containsKey(ref)) {
                filterList.add(ref);
            }
        }
        return filterList;
    }

    private String insertUrl(String url) {
        Pattern pattern = Pattern.compile(".+/");
        Matcher matcher = pattern.matcher(url);
        if (!matcher.matches()) {
            url = url + "/";
        }
        return url.replace("www.", "");
    }
}
