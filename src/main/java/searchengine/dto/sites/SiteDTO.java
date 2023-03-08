package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;
import searchengine.config.status.Status;

import java.time.LocalDateTime;


@Getter
@Setter
public class SiteDTO {
  private int id;
  private Status status;
  private String url;
  private String name;
  private String error;
  private LocalDateTime time;

}
