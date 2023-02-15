package searchengine.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.sql.PageInfo;
@Repository
public interface PageRepository extends JpaRepository<PageInfo, Integer> {
}