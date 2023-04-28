package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.SQL.Lemma;

import java.util.List;
import java.util.Optional;

@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Integer> {
//  @Query(value = "SELECT id FROM lemma WHERE site_id = :site_id", nativeQuery = true)
//  List<Integer> getId(@Param("site_id") int siteId);
  @Query(value = "SELECT * FROM lemma WHERE site_id = :site_id", nativeQuery = true)
  List<Lemma> getLemmaTable(@Param("site_id") int siteId);
  @Query(value = "SELECT * FROM lemma WHERE site_id = :site_id", nativeQuery = true)
  List<Integer> getListIdLemmaTable(@Param("site_id") int siteId);
  @Query(value = "SELECT l.id, l.frequency, l.lemma, l.site_id FROM search_engine.index i " +
          "JOIN lemma l ON l.id = i.lemma_id " +
          "WHERE i.page_id = :page_id", nativeQuery = true)
  Optional<List<Lemma>> getLemmaJoin(@Param("page_id") int pageId);


}
