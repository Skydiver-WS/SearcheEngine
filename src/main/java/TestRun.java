import searchengine.services.indexing.ParseHtmlPage;

import java.util.TreeMap;
import java.util.TreeSet;

//TODO: Тестовый класс для проверки работы. По окончанию работы удалить
public class TestRun {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    ParseHtmlPage page = new ParseHtmlPage("https://baza.team/shop/");
    for (String url:page.invoke()) {
      System.out.println(url);
    }
    System.out.println(System.currentTimeMillis() - start);
  }
}
