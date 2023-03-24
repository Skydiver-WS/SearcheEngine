package searchengine.repository.SQL;

import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.SQL.Index;
import searchengine.model.SQL.PageInfo;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {
    @Query(value = "SELECT * FROM search_engine.index WHERE page_id = :page_id", nativeQuery = true)
    List<Index> getIndex(@Param("page_id") int pageId);
}
