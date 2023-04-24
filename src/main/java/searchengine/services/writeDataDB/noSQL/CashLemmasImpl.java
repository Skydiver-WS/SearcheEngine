package searchengine.services.writeDataDB.noSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.model.SQL.Lemma;
import searchengine.model.noSQL.CashLemmas;
import searchengine.repository.noSQL.CashLemmasRepository;

import java.util.ArrayList;
import java.util.List;
@Component
public class CashLemmasImpl implements CashLemmasService {
    @Autowired
    private CashLemmasRepository cashLemmasRepository;

    @Override
    public void writeLemmas(List<Lemma> lemmaList) {
        List<CashLemmas> list = new ArrayList<>();
        lemmaList.stream().map(c -> {
                    CashLemmas cashLemmas = new CashLemmas();
                    cashLemmas.setId(c.getId());
                    cashLemmas.setFrequency(c.getFrequency());
                    cashLemmas.setLemma(c.getLemma());
                    cashLemmas.setSiteInfo(cashLemmas.getSiteInfo());
                    return cashLemmas;
                }
        ).forEach(list::add);
        cashLemmasRepository.saveAll(list);
        cashLemmasRepository.saveInDisk();
    }
}
