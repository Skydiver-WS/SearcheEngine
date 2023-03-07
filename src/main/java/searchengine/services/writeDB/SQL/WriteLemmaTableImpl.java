package searchengine.services.writeDB.SQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.Lemma;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.services.indexing.lemmaAnalyze.LemmaService;

import java.util.*;
import java.util.logging.Logger;

@Service
public class WriteLemmaTableImpl implements WriteLemmaTableService {
    @Autowired
    private LemmaRepository lemmaRepository;
    @Autowired
    private LemmaService lemmaService;

    @Override
    public void write(SiteDTO siteDTO) {
        Logger.getLogger(WriteLemmaTableImpl.class.getName()).info("Run write lemmas");
        siteDTO = lemmaService.lemma(siteDTO);
        ArrayList<Lemma> list = new ArrayList<>();
        for (String lemma:siteDTO.getFrequencyLemmas().keySet()) {
            Lemma lemmaTable = new Lemma();
            lemmaTable.setId(0);
            lemmaTable.setLemma(lemma);
            lemmaTable.setFrequency(siteDTO.getFrequencyLemmas().get(lemma));
            lemmaTable.setSiteId(siteDTO.getSiteInfo());
            list.add(lemmaTable);
        }
        lemmaRepository.saveAll(list);
    }
}
