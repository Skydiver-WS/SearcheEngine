package searchengine.services.deleteData.nosql;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.repository.nosql.CashStatisticsRepository;

import java.util.List;

@Service
public class DeleteCash implements DeleteCashService{
    @Autowired
    private CashStatisticsRepository repository;
    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
