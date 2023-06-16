package searchengine.model.sql;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
  @OnDelete(action = OnDeleteAction.CASCADE)
  private PageInfo pageId;
  @ManyToOne
  @JoinColumn(name = "lemma_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Lemma lemmaId;
  @Column(name = "\"rank\"", nullable = false)
  private float rank;
}
