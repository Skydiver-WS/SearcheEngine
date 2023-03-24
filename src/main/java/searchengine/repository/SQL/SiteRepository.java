package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.SQL.SiteInfo;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<SiteInfo, Integer> {
    @Query(value = "SELECT * FROM site WHERE url = :url", nativeQuery = true)
    Optional<SiteInfo> getSiteInfo(@Param("url") String url);

    @Modifying
    @Query(value = "UPDATE site SET status_time = :date, status = :status, last_error = :error WHERE id = :id", nativeQuery = true)
    @Transactional
    void updateStatus(@Param("id") int id, @Param("date") LocalDateTime date, @Param("status") String status, @Param("error") String error);

    @Modifying
    @Transactional
    @Query(value = "DELETE p, l, q FROM search_engine.index q " +
            "JOIN lemma l ON q.lemma_id = l.id " +
            "JOIN page p ON q.page_id = p.id " +
            "JOIN site s ON p.site_id = s.id " +
            "WHERE s.url = :url",
            nativeQuery = true)
    void delete(@Param("url") String url);

    @Modifying
    @Transactional
    @Query(value = "DELETE p FROM page p " +
            "JOIN site s ON p.site_id = s.id " +
            "WHERE s.url = :url",
            nativeQuery = true)
    void garbageClear(@Param("url") String url);
}
