package searchengine.model.SQL;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "\"index\"")
@Getter
@Setter
public class Index {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @JoinColumn(name = "page_id", nullable = false)
  private PageInfo pageId;
  @ManyToOne
  @JoinColumn(name = "lemma_id", nullable = false)
  private Lemma lemmaId;
  @Column(name = "\"rank\"", nullable = false)
  private float rank;
}
