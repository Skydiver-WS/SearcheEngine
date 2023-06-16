package searchengine.services.indexing.core.handler.lemmatable;

import org.springframework.stereotype.Component;
import searchengine.dto.sites.LemmaDTO;
import searchengine.model.sql.Lemma;
import searchengine.model.sql.SiteInfo;

import java.util.*;

/**
 * Данный метод возвращает частоту повторения лемм на странице.
 * @author Aleksandr Isaev
 */
@Component
public class HandlerDataLemma implements HandlerDataLemmaService {
    @Override
    public Map<String, Integer> frequencyLemmas(TreeMap<Integer, List<LemmaDTO>> listLemmas) {
        HashSet<LemmaDTO> list = new HashSet<>();
        Map<String, Integer> lemmas = new TreeMap<>();
        for (Integer key : listLemmas.keySet()) {
            list.addAll(listLemmas.get(key));
        }
        for (LemmaDTO lemmaDTO : list) {
            if (!lemmas.containsKey(lemmaDTO.getLemma())) {
                lemmas.put(lemmaDTO.getLemma(), 1);
            } else {
                int count = lemmas.get(lemmaDTO.getLemma()) + 1;
                lemmas.put(lemmaDTO.getLemma(), count);
            }
        }
        return lemmas;
    }

    /**
     * Метод работатет при запуске индексации одиночной страницы и
     * преднаначен для изменении параметра frequency таблицы {@link Lemma} либо добавлении леммы, при её отстутсвии в БД.
     */
    @Override
    public List<Lemma> checkNewLemmas(SiteInfo siteInfo, List<String> lemmasListNew, List<Lemma> lemmaList) {
        List<Lemma> list = new ArrayList<>(createNewLemma(lemmasListNew));
        list.addAll(lemmaList);
        Map<String, Lemma> lemmaMap = new TreeMap<>();
        for (Lemma lemma : list) {
            if (lemmaMap.containsKey(lemma.getLemma())) {
                lemma.setFrequency(lemma.getFrequency() + 1);
                lemmaMap.put(lemma.getLemma(), lemma);
            } else {
                lemma.setFrequency(lemma.getFrequency() > 0 ? lemma.getFrequency() : 1);
                lemma.setSiteId(lemma.getSiteId() == null ? siteInfo : lemma.getSiteId());
                lemmaMap.put(lemma.getLemma(), lemma);
            }
        }
        return lemmaMap.values().stream().toList();
    }

    private List<Lemma> createNewLemma(List<String> lemmasListNew) {
        List<Lemma> list = new ArrayList<>();
        for (String str : lemmasListNew) {
            Lemma lemma = new Lemma();
            lemma.setLemma(str);
            list.add(lemma);
        }
        return list;
    }
}
