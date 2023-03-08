package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.SQL.PageInfo;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<PageInfo, Integer> {
  @Query(value = "SELECT * FROM page WHERE site_id = :site_id", nativeQuery = true)
  List<PageInfo> listPageInfo(@Param("site_id") int siteId);
}
