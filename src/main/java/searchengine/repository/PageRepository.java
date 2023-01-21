package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.PageInfo;
@Repository
public interface PageRepository extends JpaRepository<PageInfo, Integer> {
}
