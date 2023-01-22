package searchengine.services.indexing;

import org.springframework.beans.factory.annotation.Autowired;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.util.Optional;

public class IndexingImpl implements IndexingService{
  @Autowired
  private PageRepository pageRepository;
  @Autowired
  private SiteRepository siteRepository;
  @Override
  public boolean getIndexing() {
    return false;
  }
  public void deleteEntry(String pathSite){
    Optional<S> one = siteRepository.findOne(pathSite);
  }

}
