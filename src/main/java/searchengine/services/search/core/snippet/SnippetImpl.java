package searchengine.services.search.core.snippet;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;
import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.model.SQL.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SnippetImpl implements SnippetService {
    //".{60}<.+?>.+?</.+?>.{60}"
    @Override
    public String getSnippet(PageInfo pageInfo, List<FrequencyLemmaDTO> list) {
        String content = Jsoup.clean(pageInfo.getContent(), Safelist.simpleText())
                .replaceAll("(<.+?>)|(</.+?>)", "")
                .replaceAll("&nbsp;", " ");
        for (FrequencyLemmaDTO dto : list) {
            String lemma = dto.getLemma();
            content = searchMath(lemma, content);
        }
        return selectSnippet(content);
    }

    private String searchMath(String lemma, String content) {
        String str = lemma;
        for (int i = 0; i < lemma.length(); i++) {
            Matcher matcher = patternMatcher(str + ".+?\\b", content);
            if (matcher.find()) {
                String match = matcher.group().trim().replaceAll("[{}().,!?:;]", "");
                try {
                    return content.replaceAll(match, " <b>" + match + "</b>");
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            } else {
                str = str.substring(0, (lemma.length() - 1) - i);
            }
        }
        return "";
    }

    private String selectSnippet(String content) {
        StringBuilder builder = new StringBuilder();
        Matcher matcher = patternMatcher("[A-ZА-Я].+?[.!?]", content);
        while (matcher.find()){
            String text = matcher.group();
            if(patternMatcher("<.+?>.+?</.+?>", text).find()){
                builder.append(text).append("...").append(" ");
            }
        }
        return builder.toString();
    }
    private Matcher patternMatcher(String regex, String input){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
