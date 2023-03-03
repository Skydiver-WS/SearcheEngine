package searchengine.services.writeDB.SQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.Lemma;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.services.indexing.lemmaAnalyze.LemmaService;

import java.util.*;
import java.util.logging.Logger;

@Service
public class WriteLemmaTable implements WriteLemmaTableService {
    @Autowired
    private LemmaRepository lemmaRepository;
    @Autowired
    private LemmaService lemmaService;

    @Override
    public void write(SiteDTO siteDTO) {
        Logger.getLogger(WriteLemmaTable.class.getName()).info("Run write lemmas");
        lemmaService.lemma(siteDTO);
        //Map<String, Integer> lemmas = lemmaAnalyze.runAnalyze(injectText(siteDTO.getPageDTO()));
        Map<String, Integer> lemmas = new HashMap<>();
        ArrayList<Lemma> list = new ArrayList<>();
        int countLemmas = 0;
        for (String text : lemmas.keySet()) {
            Lemma lemma = new Lemma();
            lemma.setId(0);
            lemma.setSiteId(siteDTO.getSiteInfo());
            lemma.setLemma(text);
            int count = lemmas.get(text);
            lemma.setFrequency(count);
            countLemmas += count;
            list.add(lemma);
        }
        siteDTO.setCountLemma(countLemmas);
        lemmaRepository.saveAll(list);
    }

}
