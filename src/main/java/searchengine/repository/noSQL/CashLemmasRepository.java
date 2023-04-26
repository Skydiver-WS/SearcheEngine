package searchengine.repository.noSQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import searchengine.model.noSQL.CashLemmas;

import java.util.List;

@Repository
public interface CashLemmasRepository extends JpaRepository<CashLemmas, Integer> {
    List<CashLemmas> findByLemma(String lemma);
}
