package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;
import searchengine.model.SQL.SiteInfo;

@Getter
@Setter
public class PageDTO {
  private int id;
  private int siteId;
  private String url;
  private int codeResponse;
  private String content;
}
