package searchengine.services.deleteDataDB.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.SiteInfo;
import searchengine.model.noSQL.CashStatisticsDB;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;
import searchengine.repository.noSQL.CashStatisticsRepository;

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
  private CashStatisticsDB cash;
  @Autowired
  private CashStatisticsRepository cashStatisticsRepository;

  @Override
  public synchronized void delete(Site site) {

    Optional<SiteInfo> siteInfo = siteRepository.getSiteInfo(site.getUrl());
    if (siteInfo.isPresent()) {
      int siteId = siteInfo.get().getId();
      List<Integer> indexList = indexRepository.getIndex(siteId);
      List<Integer> lemmaList = lemmaRepository.getListIdLemmaTable(siteId);
      List<Integer> pageInfoList = pageRepository.getListIdPageTable(siteId);
      try {
        indexRepository.deleteAllByIdInBatch(indexList);
        lemmaRepository.deleteAllByIdInBatch(lemmaList);
        pageRepository.deleteAllByIdInBatch(pageInfoList);
      }
      catch (JpaSystemException ex) {
        delete(indexList, indexRepository);
        delete(lemmaList, lemmaRepository);
        delete(pageInfoList, pageRepository);
      }
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

  private void delete(List<Integer> listId, JpaRepository<?, Integer> repository) {
    try {
      int sizeArray = 10000;
      int j = 0;
      int k = 0;
      while (true) {
        k += sizeArray;
        if (listId.size() > sizeArray) {
          List<Integer> list = listId.subList(j, k - 1);
          repository.deleteAllByIdInBatch(list);
          j += sizeArray;
        } else {
          repository.deleteAllByIdInBatch(listId);
          break;
        }
    }

    }catch (Exception e){
      e.printStackTrace();
    }
  }

}