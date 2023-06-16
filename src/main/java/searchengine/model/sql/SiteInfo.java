package searchengine.model.sql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import searchengine.config.status.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "site")
@Getter
@Setter
@Component
public class SiteInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
