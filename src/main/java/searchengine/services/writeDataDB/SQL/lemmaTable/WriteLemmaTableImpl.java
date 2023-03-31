package searchengine.services.writeDataDB.SQL.lemmaTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.LemmaRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WriteLemmaTableImpl implements WriteLemmaTableService {
    @Autowired
    private LemmaRepository lemmaRepository;


    @Override
    public void write(SiteInfo siteInfo, Map<String, Integer> lemmas) {
        ArrayList<Lemma> list = new ArrayList<>();
        for (String lemmaKey: lemmas.keySet()) {
            Lemma lemma = new Lemma();
            lemma.setLemma(lemmaKey);
            lemma.setFrequency(lemmas.get(lemmaKey));
            lemma.setSiteId(siteInfo);
            list.add(lemma);
        }
        synchronized (lemmaRepository){
            lemmaRepository.saveAll(list);
        }
    }

    @Override
    public void updateLemmaTable(List<Lemma> lemmaList) {
        lemmaRepository.saveAll(lemmaList);
    }
}
