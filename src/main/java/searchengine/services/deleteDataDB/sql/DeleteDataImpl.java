package searchengine.services.deleteDataDB.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.Lemma;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeleteDataImpl implements DeleteDataService {
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private LemmaRepository lemmaRepository;
    @Autowired
    private IndexRepository indexRepository;

    @Override
    public void delete(Site site) {
        synchronized (siteRepository) {
            siteRepository.delete(site.getUrl());
            siteRepository.garbageClear(site.getUrl());
        }
    }

    @Override
    public void delete(SiteDTO siteDTO) {
        //TODO попробовать оптимизировать обновление
        List<Lemma> list = new ArrayList<>();
        for (PageDTO pageDTO : siteDTO.getPageDTOList()) {
            Optional<List<Lemma>> index = lemmaRepository.getLemmaJoin(pageDTO.getId());
            if (index.isPresent()) {
                for (Lemma lemma : index.get()) {
                    lemma.setFrequency(lemma.getFrequency() - 1);
                    list.add(lemma);
                }
            }
        }
        lemmaRepository.saveAll(list);
        siteDTO.getPageDTOList().forEach(p -> indexRepository.delete(p.getId()));
    }
}
