package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.Index;

import javax.persistence.criteria.CriteriaBuilder;
@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {
}
