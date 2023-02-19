package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.SQL.PageInfo;
@Repository
public interface PageRepository extends JpaRepository<PageInfo, Integer> {
}
