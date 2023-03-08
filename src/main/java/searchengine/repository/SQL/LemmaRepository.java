package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;

import java.util.List;

@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Integer> {
  @Query(value = "SELECT * FROM lemma WHERE site_id = :site_id", nativeQuery = true)
  List<Lemma> getListLemma(@Param("site_id") int siteId);
}
