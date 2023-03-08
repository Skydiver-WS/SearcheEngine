package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.SQL.Index;
import searchengine.model.SQL.PageInfo;

import java.util.List;

@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {
  @Query(value = "SELECT * FROM index WHERE page_id = :page_id", nativeQuery = true)
  List<Index> listIndex(@Param("page_id") int pageId);
}
