package searchengine.repository.noSQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.noSQL.CashStatisticsDB;

@Repository

public interface CashStatisticsRepository extends JpaRepository<CashStatisticsDB, Integer> {
}
