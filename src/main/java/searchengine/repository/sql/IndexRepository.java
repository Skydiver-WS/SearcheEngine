package searchengine.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.dto.search.SearchObjectDTO;
import searchengine.model.sql.Index;

import java.util.List;

@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM search_engine.index i " +
            "WHERE i.page_id = :id", nativeQuery = true)
    void delete(@Param("id") Integer id);

    @Query(value = "SELECT id FROM search_engine.index WHERE page_id = :page_id", nativeQuery = true)
    List<Integer> getIndexId(@Param("page_id") Integer pageId);

    @Query(value = "SELECT i.id, i.rank, i.lemma_id, i.page_id FROM search_engine.index i " +
      "JOIN lemma l ON l.id = i.lemma_id " +
      "WHERE l.site_id = :site_id", nativeQuery = true)
    List<Integer> getIndex(@Param("site_id") int siteId);
    @Query(value = "SELECT i.id FROM search_engine.index i " +
            "JOIN page p ON p.id = i.page_id " +
            "JOIN site s ON s.id = p.site_id " +
            "where s.url = :url", nativeQuery = true)
    List<Long> getIndex(@Param("url") String url);

    @Query(value = "SELECT SUM(i.rank) sum FROM search_engine.index i " +
            "JOIN lemma l ON l.id = i.lemma_id " +
            "WHERE l.site_id = :site_id", nativeQuery = true)
    Integer getCountLemmas(@Param("site_id") int siteId);

    @Query("SELECT new searchengine.dto.search.SearchObjectDTO(i.id, l.id, l.lemma, l.frequency, i.rank, p.id, l.siteId.id) " +
            "FROM Index i " +
            "JOIN Lemma l ON l.id = i.lemmaId.id " +
            "JOIN PageInfo p ON p.id = i.pageId.id " +
            "JOIN SiteInfo s ON s.id = l.siteId.id " +
            "WHERE l.lemma = :lemma")
    List<SearchObjectDTO> searchMatchingLemmas(@Param("lemma") String lemma);
    @Query("SELECT new searchengine.dto.search.SearchObjectDTO(i.id, l.id, l.lemma, l.frequency, i.rank, p.id, l.siteId.id) " +
            "FROM Index i " +
            "JOIN Lemma l ON l.id = i.lemmaId.id " +
            "JOIN PageInfo p ON p.id = i.pageId.id " +
            "JOIN SiteInfo s ON s.id = l.siteId.id " +
            "WHERE i.pageId.id = :pageId")
    List<SearchObjectDTO> searchMatchingLemmas(@Param("pageId") int pageId);
}
