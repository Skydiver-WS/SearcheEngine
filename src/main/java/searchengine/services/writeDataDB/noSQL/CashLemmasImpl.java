package searchengine.services.writeDataDB.noSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import searchengine.dto.search.SearchObjectDTO;
import searchengine.model.SQL.Lemma;
import searchengine.model.noSQL.CashLemmas;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.noSQL.CashLemmasRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class CashLemmasImpl implements CashLemmasService {
    @Autowired
    private CashLemmasRepository cashLemmasRepository;
    @Autowired
    private RedisTemplate<String, List<CashLemmas>> redisTemplate;
    @Autowired
    private IndexRepository indexRepository;

    @Override
    public void writeLemmas(List<String> listLemmas) {
        List<SearchObjectDTO> lemmaList = new ArrayList<>();
        for (String lemma : listLemmas) {
            var test = indexRepository.searchMatchingLemmas(lemma);
            lemmaList.addAll(test);
        }
        List<CashLemmas> list = new ArrayList<>();
        lemmaList.stream().map(c -> {
                    CashLemmas cashLemmas = new CashLemmas();
                    cashLemmas.setId(c.getIndexId());
                    cashLemmas.setLemma(c.getLemma());
                    cashLemmas.setFrequency(c.getFrequency());
                    cashLemmas.setRank(c.getRank());
                    cashLemmas.setPageId(c.getPageId());
                    cashLemmas.setSiteId(c.getSiteId());
                    cashLemmas.setLemmaId(c.getLemmaId());
                    return cashLemmas;
                }
        ).forEach(list::add);
        cashLemmasRepository.saveAll(list);
        redisTemplate.opsForValue().set("cashLemmas", list);
    }
}
