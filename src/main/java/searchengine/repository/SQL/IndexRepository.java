package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.SQL.Index;

@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {
}
