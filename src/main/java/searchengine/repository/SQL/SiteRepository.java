package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.SQL.SiteInfo;
@Repository
public interface SiteRepository extends JpaRepository<SiteInfo, Integer> {
}
