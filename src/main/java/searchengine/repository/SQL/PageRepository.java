package searchengine.repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.dto.statistics.PageStatisticsDTO;
import searchengine.model.SQL.PageInfo;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<PageInfo, Integer> {
    @Query("SELECT COUNT(p) FROM PageInfo p WHERE p.siteId.id = :id")
    int countPage(@Param("id") int id);
    @Query("SELECT p FROM PageInfo p WHERE p.id = :id")
    PageInfo getPageInfo(@Param("id") int id);

    @Query(value = "SELECT * FROM page WHERE site_id = :site_id", nativeQuery = true)
    List<PageInfo> getListPageTable(@Param("site_id") int siteId);
    @Query(value = "SELECT * FROM page WHERE site_id = :site_id", nativeQuery = true)
    List<Integer> getListIdPageTable(@Param("site_id") int siteId);

    @Query(value = "SELECT * FROM page WHERE id = :id", nativeQuery = true)
    List<PageInfo> getContentById(@Param("id") Integer id);

    @Query(value = "SELECT * FROM page " +
            "WHERE site_id = :siteId AND path = :path", nativeQuery = true)
    Optional<PageInfo> findPage(@Param("siteId") Integer siteId, @Param("path") String path);

    @Modifying
    @Transactional
    @Query(value = "UPDATE page SET content = :content WHERE path = :path", nativeQuery = true)
    void updatePage(@Param("path") String path, @Param("content") String content);
}
