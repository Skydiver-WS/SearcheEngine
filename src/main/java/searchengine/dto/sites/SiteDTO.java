package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;
import searchengine.config.status.Status;
import searchengine.model.SQL.SiteInfo;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class SiteDTO {
  private int idSite;
  private int countLemma;
  private Status status;
  private String url;
  private String name;
  private String error;
  private LocalDateTime time;
  private List<PageDTO> pageDTO;
  private SiteInfo siteInfo;
}
