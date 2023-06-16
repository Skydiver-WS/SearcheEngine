package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;
import searchengine.model.sql.Lemma;
import searchengine.model.sql.PageInfo;

@Getter
@Setter
public class IndexDTO {
  private PageInfo pageInfo;
  private Lemma lemma;
  private float rank;
}
