package searchengine.model.sql;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "lemma")
@Getter
@Setter
public class Lemma {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @JoinColumn(name = "site_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private SiteInfo siteId;
  @Column(nullable = false)
  private String lemma;
  @Column(nullable = false)
  private int frequency;
}
