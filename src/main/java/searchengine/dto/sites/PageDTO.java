package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageDTO {
  private int id;
  private String url;
  private int codeResponse;
  private String content;
}
