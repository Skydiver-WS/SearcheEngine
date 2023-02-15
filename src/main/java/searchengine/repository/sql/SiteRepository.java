package searchengine.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.sql.SiteInfo;
@Repository
public interface SiteRepository extends JpaRepository<SiteInfo, Integer> {
}