package searchengine.services.search.core.result.snippet;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;
import searchengine.dto.search.FrequencyLemmaDTO;
import searchengine.dto.search.IndexResultDTO;
import searchengine.model.SQL.PageInfo;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Service
public class SnippetImpl implements SnippetService {
    private final String REGEX = "[()\\[\\]{}&|+^?]";

    @Override
    public String getSnippet(PageInfo pageInfo, List<FrequencyLemmaDTO> list) {
        List<Integer[]> listIndexNumber = new ArrayList<>();
        String[] content = Jsoup.clean(pageInfo.getContent(), Safelist.simpleText())
                .replaceAll("(<.+?>)|(</.+?>)", "")
                .replaceAll("&nbsp;", " ")
                .replaceAll("-", " - ")
                .split("\\s+");
        for (FrequencyLemmaDTO dto : list) {
            String lemma = dto.getLemma();
            Integer[] indices = Arrays.stream(searchMath(lemma, content)).toList().toArray(new Integer[0]);
            listIndexNumber.add(indices);
        }
        listIndexNumber.sort(Comparator.comparingInt(i -> i.length));
        List<IndexResultDTO> queryAccuracy = queryAccuracy(listIndexNumber);
        return selectSnippet(queryAccuracy, content);
    }

    private Integer[] searchMath(String lemma, String[] content) {
        double percent = 80;
        int div = lemma.length() - (int) (lemma.length() * percent) / 100;
        String finalLemma = lemma.substring(0, lemma.length() - div);
        return IntStream.range(0, content.length)
                .filter(j -> patternMatcher(finalLemma, content[j].replaceAll(REGEX, "").toLowerCase()).find(0))
                .boxed()
                .toArray(Integer[]::new);
    }

    private List<IndexResultDTO> queryAccuracy(List<Integer[]> listIndex) {
        List<IndexResultDTO> listObj = new ArrayList<>();
        for (Integer i: listIndex.get(0)) {
            IndexResultDTO dto = new IndexResultDTO();
            List<Integer> list = new ArrayList<>();
            list.add(i);
            for (int j = 1; j < listIndex.size(); j++) {
                Integer[] arrayIndex = listIndex.get(j);
                int index = Arrays.binarySearch(arrayIndex, i);
                if(index < 0){
                    index = Math.abs(index + 1);
                }
                int result;
                if (index == 0){
                    result = arrayIndex[0];
                } else if (index == arrayIndex.length) {
                    result = arrayIndex[arrayIndex.length - 1];
                } else {
                    int diff1 = Math.abs(arrayIndex[index] - i);
                    int diff2 = Math.abs(arrayIndex[index - 1] - i);
                    result = diff1 <= diff2 ? arrayIndex[index] : arrayIndex[index - 1];
                }
                list.add(result);
            }
            list.sort(Comparator.naturalOrder());
            int accuracy = Math.abs(list.stream().reduce((a, b) -> a - b).orElse(0));
            dto.setAccuracy(accuracy);
            dto.setListIndex(list);
            listObj.add(dto);
        }
        return listObj;
    }

    private String selectSnippet(List<IndexResultDTO> list, String[] content) {
        List<String> contentList = new ArrayList<>(Arrays.asList(content));
        StringBuilder builder = new StringBuilder();
        TreeSet<Integer> finalList = new TreeSet<>();
        for (IndexResultDTO result : list) {
            Integer[] index = result.getListIndex().toArray(new Integer[0]);
            Arrays.stream(index).forEach(i -> {
                String text = contentList.get(i).replaceAll(REGEX, " ");
                text = text.replaceAll(text, " <b>" + text + "</b> ");
                contentList.set(i, text);
                int start = i - 6;
                int end = i + 6;
                start = Math.max(start, 0);
                end = Math.min(end, (contentList.size() - 1));
                IntStream.range(start, end).forEach(finalList::add);
            });
        }
        finalList.stream().limit(50).forEach(i -> builder.append(" ").append(contentList.get(i)).append(" "));
        return builder.toString();
    }

    private Matcher patternMatcher(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
