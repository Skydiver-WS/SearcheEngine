package searchengine.services.deleteDataDB.nosql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.noSQL.CashLemmasRepository;

import java.util.List;

@Component
public class DeleteCashLemmasImpl implements DeleteCashLemmasService{
    @Autowired
    private IndexRepository indexRepository;
    @Autowired
    private CashLemmasRepository cashLemmasRepository;

    @Override
    public void delete() {
        List<Long> listIndexId = indexRepository.getIndex();
        listIndexId.forEach(id -> cashLemmasRepository.deleteById(Math.toIntExact(id)));
        cashLemmasRepository.deleteAll();
    }
}
