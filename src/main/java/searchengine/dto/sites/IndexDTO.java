package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;

@Getter
@Setter
public class IndexDTO {
  private PageInfo pageInfo;
  private Lemma lemma;
  private float rank;
}
