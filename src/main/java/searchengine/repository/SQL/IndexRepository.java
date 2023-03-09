package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.SQL.Index;
import searchengine.model.SQL.PageInfo;

import java.util.List;

@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {
  @Query(value = "SELECT id FROM search_engine.index WHERE lemma_id = :lemma_id", nativeQuery = true)
  Integer getId(@Param("lemma_id") int lemmaId);
}
