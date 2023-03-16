package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.config.status.Status;
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
}
