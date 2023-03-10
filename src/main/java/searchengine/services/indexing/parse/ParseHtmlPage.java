package searchengine.services.indexing.parse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import searchengine.dto.sites.PageDTO;
import searchengine.services.indexing.IndexingImpl;

import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class ParseHtmlPage extends RecursiveTask<Set<PageDTO>>{
    private final Set<PageDTO> finalWebStructure = new HashSet<>();

    @NonNull
    private String url;

    @SneakyThrows
    @Override
    protected Set<PageDTO> compute() {
        if (checkUrl(url) && IndexingImpl.isAliveThread()) {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows; U; WindowsNT" +
                            "5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com").get(); // TODO: вынести в конфигурацию
            addNewPage(doc);
            List<String> listAllRef = doc.select("a").eachAttr("abs:href");
            TreeSet<String> checkRef = filterSite(listAllRef);
            ArrayList<ParseHtmlPage> pages = fork(checkRef);
            join(pages);
            Logger.getLogger(ParseHtmlPage.class.getName()).info(Thread.currentThread().isAlive() + " - "
                    + "Complete");
            return finalWebStructure;
        }
        return null;
    }

    @SneakyThrows
    private ArrayList<ParseHtmlPage> fork(Set<String> checkRef) {
        ArrayList<ParseHtmlPage> pages = new ArrayList<>();
        for (String url : checkRef) {
            if (checkUrl(url)) {
                Thread.sleep(500);
                ParseHtmlPage htmlPage = new ParseHtmlPage(url);
                htmlPage.fork();
                pages.add(htmlPage);
                Thread.currentThread().setName(url);
                Logger.getLogger(ParseHtmlPage.class.getName()).info(Thread.currentThread().isAlive()
                        + " - " + url + " FORK complete");
            }
        }
        return pages;
    }

    private void join(ArrayList<ParseHtmlPage> pages) {
        for (ParseHtmlPage page : pages) {
            try {
                finalWebStructure.addAll(page.join());
                Logger.getLogger(ParseHtmlPage.class.getName()).info(Thread.currentThread().isAlive()
                        + " - " + url + " JOIN complete");
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
            if (matcher.find() && checkUrl(ref)) {
                filterList.add(ref);
            }
        }
        return filterList;
    }



    private void addNewPage(Document doc) {
        PageDTO pageDTO = new PageDTO();
        pageDTO.setUrl(url);
        pageDTO.setCodeResponse(doc.connection().response().statusCode());
        pageDTO.setContent(doc.html());
        finalWebStructure.add(pageDTO);
    }
     private boolean checkUrl(String url){
         for (PageDTO page:finalWebStructure) {
             return !page.getUrl().equals(url);
         }
         return true;
     }


}
