package searchengine.repository.nosql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.nosql.CashStatisticsDB;

@Repository

public interface CashStatisticsRepository extends JpaRepository<CashStatisticsDB, Integer> {
}
