package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import searchengine.config.Status;

import java.time.LocalDateTime;
@Component
@Getter
@Setter
public class SiteDTO {
  private Status status;
  private String url;
  private String name;
  private LocalDateTime time;
}
