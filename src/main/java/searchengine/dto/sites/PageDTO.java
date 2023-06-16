package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;
import searchengine.model.sql.SiteInfo;

@Getter
@Setter
public class PageDTO {
  private Integer id;
  private String url;
  private int codeResponse;
  private String content;
  private SiteInfo siteInfo;
}
