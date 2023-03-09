package searchengine.services.deleteDataInDB.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import searchengine.config.status.Status;
import searchengine.dto.sites.IndexDTO;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.Index;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.indexing.IndexingService;
import searchengine.services.indexing.lemmaAnalyze.LemmaService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

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

    @Override
    public void delete(SiteDTO siteDTO) {
        int siteId = siteRepository.getId("https://www.playback.ru");
        List<Integer> listPageId = pageRepository.getListId(siteId);
        List<Integer> listLemmaId = lemmaRepository.getId(siteId);
        List<Integer> listIndexId = getIdIndexTable(listLemmaId);
        changeSite(siteId);
        deleteIndex(listIndexId);
        deleteLemma(listLemmaId);
        deletePage(listPageId);
    }

    private List<Integer> getIdIndexTable(List<Integer> lemmaList) {
        ArrayList<Integer> listId = new ArrayList<>();
        for (Integer indexId : lemmaList) {
            listId.add(indexRepository.getId(indexId));
        }
        return listId;
    }

    private void deleteIndex(List<Integer> listIndexId) {
        if (listIndexId.size() > 0) {
            indexRepository.deleteAllByIdInBatch(listIndexId);
        }
    }

    private void deleteLemma(List<Integer> listLemmaId) {
        if (listLemmaId.size() > 0) {
            lemmaRepository.deleteAllByIdInBatch(listLemmaId);
        }
    }

    private void deletePage(List<Integer> listPageId) {
        if (listPageId.size() > 0) {
            pageRepository.deleteAllByIdInBatch(listPageId);
        }
    }
    private void changeSite(Integer siteId){
        Optional<SiteInfo> site = siteRepository.findById(siteId);
        if(site.isPresent()){
            SiteInfo siteInfo = site.get();
            siteInfo.setStatus(Status.INDEXING);
            siteInfo.setStatusTime(LocalDateTime.now());
            siteInfo.setLastError(null);
            siteRepository.save(siteInfo);
        }
    }
}
