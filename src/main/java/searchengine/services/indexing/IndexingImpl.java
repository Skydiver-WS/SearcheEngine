package searchengine.services.indexing;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.model.PageInfo;
import searchengine.model.SiteInfo;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.util.ArrayList;
import java.util.List;

public class IndexingImpl implements IndexingService{
  @Autowired
  private PageRepository pageRepository;
  @Autowired
  private SiteRepository siteRepository;
  @Autowired
  private SitesList sitesList;

  @Override
  public boolean getIndexing() {
    return false;
  }
  private List <SiteInfo> sitesInfo(){
    return siteRepository.findAll();
  }

  private List <PageInfo> pageInfo(){
    return pageRepository.findAll();
  }
  private void deleteSite(String site){
    for (SiteInfo siteInfo:sitesInfo()) {
      if(siteInfo.getName().equals(site)){
        deletePages(siteInfo.getId());
        siteRepository.deleteById(siteInfo.getId());
      }
    }
  }

  private void deletePages(int siteId){
    for (PageInfo pageInfo:pageInfo()) {
      if(pageInfo.getSiteId().getId() == siteId){
        pageRepository.deleteById(pageInfo.getId());
      }
    }
    pageRepository.deleteById(siteId);
  }


  private void parse (){

  }


}