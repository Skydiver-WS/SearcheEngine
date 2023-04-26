package searchengine.services.deleteDataDB.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.status.Status;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.Index;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.SiteInfo;
import searchengine.model.noSQL.CashStatisticsDB;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;
import searchengine.repository.noSQL.CashLemmasRepository;
import searchengine.repository.noSQL.CashStatisticsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeleteDataImpl implements DeleteDataService {
    @Autowired
    private IndexRepository indexRepository;
    @Autowired
    private LemmaRepository lemmaRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SiteRepository siteRepository;
    private int siteId;

    @Override
    public void delete(Site site) {
        Optional<SiteInfo> siteInfo = siteRepository.getSiteInfo(site.getUrl());
        if (siteInfo.isPresent()) {
            siteId = siteInfo.get().getId();
            List<Integer> listPageId = pageRepository.getListId(siteId);
            List<Integer> listLemmaId = lemmaRepository.getId(siteId);
            List<Integer> listIndexId = getIdIndexTable(listPageId);
            changeSite(siteId);
            deleteIndex(listIndexId);
            deleteLemma(listLemmaId);
            deletePage(listPageId);
        }
    }

    @Override
    public void delete(SiteDTO siteDTO) {
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

    private List<Integer> getIdIndexTable(List<Integer> listPageId) {
        ArrayList<Index> listIndex = new ArrayList<>();
        List<Integer> listId = new ArrayList<>();
        for (Integer id : listPageId) {
            listIndex.addAll(indexRepository.getIndex(id));
        }
        for (Index index : listIndex) {
            listId.add(index.getId());
        }
        return listId;
    }

    private synchronized void deleteIndex(List<Integer> listIndexId) {
        if (listIndexId.size() > 0) {
            indexRepository.deleteAllByIdInBatch(listIndexId);
        }
    }

    private synchronized void deleteLemma(List<Integer> listLemmaId) {
        if (listLemmaId.size() > 0) {
            lemmaRepository.deleteAllByIdInBatch(listLemmaId);
        }
    }

    private synchronized void deletePage(List<Integer> listPageId) {
        if (listPageId.size() > 0) {
            pageRepository.deleteAllByIdInBatch(listPageId);
        }
    }

    private void changeSite(Integer siteId) {
        Optional<SiteInfo> site = siteRepository.findById(siteId);
        if (site.isPresent()) {
            SiteInfo siteInfo = site.get();
            siteInfo.setStatus(Status.INDEXING);
            siteInfo.setStatusTime(LocalDateTime.now());
            siteInfo.setLastError(null);
            siteRepository.save(siteInfo);
        }
    }
}