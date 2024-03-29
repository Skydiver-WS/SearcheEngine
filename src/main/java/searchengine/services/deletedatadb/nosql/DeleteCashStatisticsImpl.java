package searchengine.services.deletedatadb.nosql;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.repository.nosql.CashStatisticsRepository;

@Service
public class DeleteCashStatisticsImpl implements DeleteCashStatisticsService {
    @Autowired
    private CashStatisticsRepository repository;
    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
