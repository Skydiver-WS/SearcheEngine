package searchengine.repository.noSQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import searchengine.model.noSQL.CashLemmas;
@Repository
public interface CashLemmasRepository extends JpaRepository<CashLemmas, Integer> {
    @Query("BGSAVE")
    void saveInDisk();
}
