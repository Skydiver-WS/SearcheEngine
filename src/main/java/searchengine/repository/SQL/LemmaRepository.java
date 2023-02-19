package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.SQL.Lemma;
@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Integer> {
}
