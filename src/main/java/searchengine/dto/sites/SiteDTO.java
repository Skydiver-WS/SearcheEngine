package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;
import searchengine.config.Status;

import java.time.LocalDateTime;
import java.util.Map;


@Getter
@Setter
public class SiteDTO {
  private Status status;
  private String url;
  private String name;
  private LocalDateTime time;

  private Map<String, String> content;
  private int response;
}
