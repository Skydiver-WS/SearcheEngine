package searchengine.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.sql.Lemma;
@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Integer> {
}
