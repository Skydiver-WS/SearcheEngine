package searchengine.repository.SQL;

import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.dto.search.SearchObjectDTO;
import searchengine.model.SQL.Index;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM search_engine.index i " +
            "WHERE i.page_id = :id", nativeQuery = true)
    void delete(@Param("id") int id);

//    @Query(value = "SELECT * FROM search_engine.index WHERE page_id = :page_id", nativeQuery = true)
//    List<Index> getIndex(@Param("page_id") int pageId);

    @Query(value = "SELECT i.id, i.rank, i.lemma_id, i.page_id FROM search_engine.index i " +
      "JOIN lemma l ON l.id = i.lemma_id " +
      "WHERE l.site_id = :site_id", nativeQuery = true)
    List<Integer> getIndex(@Param("site_id") int siteId);

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
}
