package searchengine.services.writeDataInDB.SQL.indexTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.LemmaDTO;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;

import java.util.List;
import java.util.Set;

@Service
public class WriteIndexTableImpl implements WriteIndexTableService {
  @Autowired
  private LemmaRepository lemmaRepository;
  @Autowired
  private PageRepository pageRepository;
  @Autowired
  private IndexRepository indexRepository;

  @Override
  public void write(SiteInfo siteInfo, Set<List<LemmaDTO>> lemmas) {

  }
}
