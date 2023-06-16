package searchengine.repository.nosql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.nosql.CashLemmas;

import java.util.List;

@Repository
public interface CashLemmasRepository extends JpaRepository<CashLemmas, Integer> {
    List<CashLemmas> findByLemma(String lemma);
}
