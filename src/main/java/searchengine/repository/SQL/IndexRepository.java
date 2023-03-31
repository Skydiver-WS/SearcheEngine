package searchengine.repository.SQL;

import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.SQL.Index;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM search_engine.index i " +
            "WHERE i.page_id = :id", nativeQuery = true)
    void delete(@Param("id") int id);
}
