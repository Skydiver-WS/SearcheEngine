package searchengine.model;

import lombok.Getter;
import lombok.Setter;
import searchengine.config.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "site")
@Getter
@Setter
public class SiteInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;
  @Column(nullable = false)
  private LocalDateTime statusTime;
  @Column(columnDefinition = "TEXT")
  private String lastError;
  @Column(nullable = false)
  private String url;
  @Column(nullable = false)
  private String name;
}
