package searchengine.services.deleteDataInDB.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.IndexDTO;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.indexing.IndexingService;
import searchengine.services.indexing.lemmaAnalyze.LemmaService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
  private SiteDTO siteDTO;
  private PageDTO pageDTO;
  private LemmaDTO lemmaDTO;
  private IndexDTO indexDTO;
  private int siteId;

  @Override
  public void delete(SiteDTO siteDTO) {
    this.siteDTO = siteDTO;
    SiteInfo siteInfo = siteRepository.getSiteInfo(siteDTO.getUrl());
    siteId = siteInfo.getId();
    List<PageInfo> pageInfoList = pageRepository.listPageInfo(siteId);
    List<Lemma> lemmaList = lemmaRepository.getListLemma(siteId);
  }
  private void deleteIndexTable(List<PageInfo> pageInfoList){
    for (PageInfo pageInfo:pageInfoList) {

    }
  }
}
