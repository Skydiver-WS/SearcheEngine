package searchengine.services.writedatadb.nossql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import searchengine.dto.search.SearchObjectDTO;
import searchengine.model.nosql.CashLemmas;
import searchengine.repository.sql.IndexRepository;
import searchengine.repository.nosql.CashLemmasRepository;

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
            var getInfoTableList = indexRepository.searchMatchingLemmas(lemma);
            lemmaList.addAll(getInfoTableList);
        }
        List<CashLemmas> list = createCashObjList(lemmaList);
        synchronized (cashLemmasRepository) {
            cashLemmasRepository.saveAll(list);
            redisTemplate.opsForValue().set("cashLemmas", list);
        }
    }

    @Override
    public void writeLemmas(int pageId) {
        var getInfoTableList = indexRepository.searchMatchingLemmas(pageId);
        List<SearchObjectDTO> lemmaList = new ArrayList<>(getInfoTableList);
        List<CashLemmas> list = createCashObjList(lemmaList);
        synchronized (cashLemmasRepository) {
            cashLemmasRepository.saveAll(list);
            redisTemplate.opsForValue().set("cashLemmas", list);
        }
    }

    private List<CashLemmas> createCashObjList(List<SearchObjectDTO> lemmaList) {
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
        return list;
    }
}
