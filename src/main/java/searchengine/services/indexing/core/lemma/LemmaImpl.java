package searchengine.services.indexing.core.lemma;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.LemmaDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.repository.SQL.PageRepository;
import searchengine.services.indexing.core.lemma.injectText.InjectText;

import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.logging.Logger;

@Service
public class LemmaImpl implements LemmaService {
  @Autowired
  private PageRepository pageRepository;

  @SneakyThrows
  @Override
  public  TreeMap<Integer, List<LemmaDTO>> getListLemmas(int siteId) {
    List<PageInfo> listContent = pageRepository.getContent(siteId);
    ArrayList<ForkJoinTask<TreeMap<Integer, List<LemmaDTO>>>> listInjectClass = new ArrayList<>();
    TreeMap<Integer, List<LemmaDTO>> allLemmas = new TreeMap<>();
    for (PageInfo page : listContent) {
      InjectText injectText = new InjectText(page);
      listInjectClass.add(injectText.fork());
    }
    for (ForkJoinTask<TreeMap<Integer, List<LemmaDTO>>> text : listInjectClass) {
      allLemmas.putAll(text.join());
      Logger.getLogger(InjectText.class.getName()).info(Thread.currentThread().getName() + " - finish");
    }
    return allLemmas;
  }
}
