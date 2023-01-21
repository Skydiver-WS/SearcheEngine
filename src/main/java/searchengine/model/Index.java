package searchengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "search_index")
@Getter
@Setter
public class Index {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @OneToOne
  @JoinColumn(name = "page_id")
  private PageInfo pageId;
  @ManyToOne
  @JoinColumn(name = "lemma_id", nullable = false)
  private Lemma lemmaId;
  @Column(name = "rank_page", nullable = false)
  private float rank;
}
