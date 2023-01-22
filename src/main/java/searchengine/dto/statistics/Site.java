package searchengine.dto.statistics;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import searchengine.model.PageInfo;
import searchengine.model.SiteInfo;

@Getter
@Setter
public class Site {
  private SiteInfo siteInfo;
  private PageInfo pageInfo;

  @Override
  public String toString() {
    return siteInfo.getUrl() + pageInfo.getPath();
  }
}
