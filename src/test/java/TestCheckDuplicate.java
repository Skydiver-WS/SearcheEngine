import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import searchengine.dto.sites.PageDTO;
import searchengine.services.indexing.core.check.duplicateUrl.CheckDuplicateRef;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@SpringBootTest(classes = CheckDuplicateRef.class)
public class TestCheckDuplicate {
    private final List<PageDTO> list = addNewPageDto();
    private final CheckDuplicateRef check = new CheckDuplicateRef(list);

    @Test
    @DisplayName("Проверка на отсутсвие дубликотов url в List<PageDTO>")
    public void getList() {
        ArrayList<String> url = new ArrayList<>();
        for (PageDTO page : check.getList()) {
            url.add(page.getUrl());
        }
        Assertions.assertArrayEquals(url.toArray(), notDuplicateUrl());
    }
    private List<PageDTO> addNewPageDto() {
        List<PageDTO> pageDTOList = new ArrayList<>();
        String[] listUrl = {"https://lenta.ru/",
                "https://lenta.ru/news/2023/03/02/secret_of_longevity/",
                "https://lenta.ru/news/2023/03/02/blinken_lavrov/",
                "https://lenta.ru/news/2023/03/02/uchitel/",
                "https://lenta.ru/news/2023/03/02/vyborggg/",
                "https://lenta.ru/news/2023/03/02/secret_of_longevity/",
                "https://lenta.ru/news/2023/03/02/vradii/",
                "https://lenta.ru/news/2023/03/02/blinken_lavrov/",
                "https://lenta.ru/news/2023/03/02/vyborggg/",
                "https://lenta.ru/"};//del 8
        for (String s : listUrl) {
            PageDTO pageDTO = new PageDTO();
            pageDTO.setUrl(s);
            pageDTOList.add(pageDTO);
        }
        return pageDTOList;
    }

    private String[] notDuplicateUrl() {
        return new String[]{"https://lenta.ru/",
                "https://lenta.ru/news/2023/03/02/blinken_lavrov/",
                "https://lenta.ru/news/2023/03/02/secret_of_longevity/",
                "https://lenta.ru/news/2023/03/02/uchitel/",
                "https://lenta.ru/news/2023/03/02/vradii/",
                "https://lenta.ru/news/2023/03/02/vyborggg/"};
    }
}
