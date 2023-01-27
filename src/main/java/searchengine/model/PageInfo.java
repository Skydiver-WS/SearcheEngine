package searchengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Index;

@Entity
@Table(name = "page")
@Getter
@Setter
public class PageInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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
