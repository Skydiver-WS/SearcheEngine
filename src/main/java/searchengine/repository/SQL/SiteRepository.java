package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.SQL.SiteInfo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<SiteInfo, Integer> {
  @Query(value = "SELECT * FROM site WHERE url = :url", nativeQuery = true)
  Optional<SiteInfo> getSiteInfo(@Param("url") String url);
}
