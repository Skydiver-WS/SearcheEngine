package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.SQL.SiteInfo;

import java.util.Collection;

@Repository
public interface SiteRepository extends JpaRepository<SiteInfo, Integer> {
  @Query(value = "SELECT * FROM site WHERE url = :url", nativeQuery = true)
  SiteInfo getSiteInfo(@Param("url") String url);
}
