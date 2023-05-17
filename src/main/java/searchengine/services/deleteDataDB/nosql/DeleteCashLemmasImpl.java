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
    public void delete(String url) {
        List<Long> listIndexId = indexRepository.getIndex(url);
        listIndexId.forEach(id -> cashLemmasRepository.deleteById(Math.toIntExact(id)));
    }
    @Override
    public void delete(Integer pageId) {
        List<Integer> listIndexId = indexRepository.getIndexId(pageId);
        listIndexId.forEach(id -> cashLemmasRepository.deleteById(Math.toIntExact(id)));
    }
}
