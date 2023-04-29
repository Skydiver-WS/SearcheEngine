package searchengine.services.indexing.core.lemma;

import lombok.SneakyThrows;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.PageDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.repository.SQL.PageRepository;
import searchengine.services.indexing.core.lemma.injectText.InjectText;

import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class LemmaImpl implements LemmaService {
  @Autowired
  private PageRepository pageRepository;

  @SneakyThrows
  @Override
  public  TreeMap<Integer, List<LemmaDTO>> getListLemmas(int siteId) {
    List<PageInfo> listContent = pageRepository.getListPageTable(siteId);
    return getList(listContent);
  }

  @Override
  public TreeMap<Integer, List<LemmaDTO>> getListLemmas(List<PageDTO> list) {
    List<PageInfo> listContent = list.stream()
            .map(PageDTO::getId)
            .flatMap(id -> pageRepository.getContentById(id).stream())
            .toList();
    return getList(listContent);
  }

  @Override
  public Map<String, Integer> getListLemmas (String query){
    InjectText injectText = new InjectText(query, init());
    return injectText.getLemmas();
  }

  private TreeMap<Integer, List<LemmaDTO>> getList(List<PageInfo> listContent){
    ArrayList<ForkJoinTask<TreeMap<Integer, List<LemmaDTO>>>> listInjectClass = new ArrayList<>();
    TreeMap<Integer, List<LemmaDTO>> allLemmas = new TreeMap<>();
    LuceneMorphology [] morphologies = init();
    for (PageInfo page : listContent) {
      InjectText injectText = new InjectText(page, morphologies);
      listInjectClass.add(injectText.fork());
    }
    for (ForkJoinTask<TreeMap<Integer, List<LemmaDTO>>> text : listInjectClass) {
      allLemmas.putAll(text.join());
      Logger.getLogger(InjectText.class.getName()).info(Thread.currentThread().getName() + " - finish");
    }
    return allLemmas;
  }
  @SneakyThrows
  private LuceneMorphology [] init(){
    return new LuceneMorphology[]{
    new RussianLuceneMorphology(),
    new EnglishLuceneMorphology()};
  }
}
