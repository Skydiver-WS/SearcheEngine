package searchengine.services.writedatadb.sql.lemmatable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.model.sql.Lemma;
import searchengine.model.sql.SiteInfo;
import searchengine.repository.sql.LemmaRepository;


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
            lemmaRepository.saveAllAndFlush(list);
        }
    }

    @Override
    public void updateLemmaTable(List<Lemma> lemmaList) {
        lemmaRepository.saveAll(lemmaList);
    }
}
