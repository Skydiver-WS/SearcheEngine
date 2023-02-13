package searchengine.model;

import lombok.Getter;
import lombok.Setter;

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
  @JoinColumn(name = "site_id")
  private SiteInfo siteId;
  @Column(nullable = false)
  private String lemma;
  @Column(nullable = false)
  private int frequency;
}
