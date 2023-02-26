package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDTO {
  private String url;
  private int codeResponse;

  private String content;
}
