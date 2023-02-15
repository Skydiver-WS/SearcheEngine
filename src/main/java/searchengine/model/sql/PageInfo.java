package searchengine.model.sql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "page")
@Getter
@Setter
@Component
public class PageInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @JoinColumn(name = "site_id", nullable = false)
  private SiteInfo siteId;
  @Column(columnDefinition = "TEXT NOT NULL, UNIQUE KEY (path(512))")
  private String path;
  @Column(nullable = false)
  private int code;
  @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
  private String content;
}
