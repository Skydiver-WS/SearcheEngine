import org.springframework.http.HttpStatus;
import searchengine.services.indexing.ParseHtmlPage;

import java.util.Arrays;

//TODO: Тестовый класс для проверки работы. По окончанию работы удалить
public class TestRun {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    ParseHtmlPage page = new ParseHtmlPage("https://baza.team/shop/");
//    for (String url:page.invoke().keySet()) {
//      System.out.println(url + " - " + page.invoke().get(url));
//    }
    String test = page.invoke().keySet().toString();
    System.out.println(test.replace("[]", ""));
//    System.out.println(page.invoke());
//    System.out.println(System.currentTimeMillis() - start);
//    System.out.println(HttpStatus.BAD_GATEWAY.value() + " - " + HttpStatus.NOT_FOUND);

  }
}
