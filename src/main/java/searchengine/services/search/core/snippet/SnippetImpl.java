package searchengine.services.search.core.snippet;

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
    private final String REGEX = "[()\\[\\]{}&|+^]";

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
        return "";
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
        for (int i = 0; i < listIndex.get(0).length; i++) {
            List<Integer> list = new ArrayList<>();
            int indexI = listIndex.get(0)[i];
            list.add(indexI);
            IndexResultDTO obj = new IndexResultDTO();
            for (int j = 1; j < listIndex.size(); j++) {
                int indexJ = i < listIndex.get(j).length ? listIndex.get(j)[i] : 0;
                list.add(indexJ);
            }
            obj.setListIndex(list);

        }

        return null;
    }

    private String selectSnippet(List<IndexResultDTO> list, String[] content) {
        List<String> contentList = new ArrayList<>(Arrays.asList(content));
        StringBuilder builder = new StringBuilder();
        for (IndexResultDTO result : list) {
            Integer[] index = result.getListIndex().toArray(new Integer[0]);
            Arrays.stream(index).forEach(i -> {
                String text = contentList.get(i).replaceAll(REGEX, " ");
                text = text.replaceAll(text, " <b>" + text + "</b> ");
                contentList.set(i, text);
            });
            int start = index[0] - 6;
            int end = index[index.length - 1] + 6;
            start = Math.max(start, 0);
            end = Math.min(end, (contentList.size() - 1));
            List<String> finalList = contentList.subList(start, end);
            finalList.forEach(i -> builder.append(" ").append(i).append(" "));
        }
        return builder.toString();
    }

    private Matcher patternMatcher(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

//    @Override
//    public String getSnippet(PageInfo pageInfo, List<FrequencyLemmaDTO> list) {
//        List<Integer> listIndexNumber = new ArrayList<>();
//        String[] content = Jsoup.clean(pageInfo.getContent(), Safelist.simpleText())
//                .replaceAll("(<.+?>)|(</.+?>)", "")
//                .replaceAll("&nbsp;", " ")
//                .replaceAll("-", " - ")
//                .split("\\s+");
//        for (FrequencyLemmaDTO dto : list) {
//            String lemma = dto.getLemma();
//            List<Integer> indices = Arrays.stream(searchMath(lemma, content)).toList();
//            listIndexNumber.addAll(indices);
//        }
//        listIndexNumber.sort(Comparator.naturalOrder());
//        List<IndexResultDTO> queryAccuracy = queryAccuracy(listIndexNumber);
//        return queryAccuracy == null ? "" : selectSnippet(queryAccuracy, content);
//    }
//
//    private Integer[] searchMath(String lemma, String[] content) {
//        double percent = 80;
//        int div = lemma.length() - (int) (lemma.length() * percent) / 100;
//        String finalLemma = lemma.substring(0, lemma.length() - div);
//        return IntStream.range(0, content.length)
//                .filter(j -> patternMatcher(finalLemma, content[j].replaceAll(REGEX, "").toLowerCase()).find(0))
//                .boxed()
//                .toArray(Integer[]::new);
//    }
//
//    private List<IndexResultDTO> queryAccuracy(List<Integer> listIndex) {
//        int constCh = 30;
//        List<IndexResultDTO> list = new ArrayList<>();
//        for (int i = 0; i < listIndex.size(); i++) {
//            List<Integer> index = new ArrayList<>();
//            index.add(listIndex.get(i));
//            for (int j = i + 1; j < listIndex.size(); j++) {
//                int interval = Math.abs(listIndex.get(i) - listIndex.get(j));
//                if (interval <= constCh) {
//                    IndexResultDTO resultDTO = new IndexResultDTO();
//                    index.add(listIndex.get(j));
//                    resultDTO.setListIndex(index);
//                    resultDTO.setAccuracy(interval);
//                    list.add(resultDTO);
//                } else {
//                    break;
//                }
//            }
//        }
//        list.sort(Comparator.comparingInt(IndexResultDTO::getAccuracy));
//        return list.size() > 0 ? list.subList(0, 3 > list.size() ? list.size() - 1 : 3) : null;
//    }
//
//    private String selectSnippet(List<IndexResultDTO> list, String[] content) {
//        List<String> contentList = new ArrayList<>(Arrays.asList(content));
//        StringBuilder builder = new StringBuilder();
//        for (IndexResultDTO result : list) {
//            Integer[] index = result.getListIndex().toArray(new Integer[0]);
//            Arrays.stream(index).forEach(i -> {
//                String text = contentList.get(i).replaceAll(REGEX, " ");
//                text = text.replaceAll(text, " <b>" + text + "</b> ");
//                contentList.set(i, text);
//            });
//            int start = index[0] - 6;
//            int end = index[index.length - 1] + 6;
//            start = Math.max(start, 0);
//            end = Math.min(end, (contentList.size() - 1));
//            List<String> finalList = contentList.subList(start, end);
//            finalList.forEach(i -> builder.append(" ").append(i).append(" "));
//        }
//        return builder.toString();
//    }
//
//    private Matcher patternMatcher(String regex, String input) {
//        Pattern pattern = Pattern.compile(regex);
//        return pattern.matcher(input);
//    }
}
