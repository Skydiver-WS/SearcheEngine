package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;
import searchengine.config.status.Status;
import searchengine.model.SQL.PageInfo;
import searchengine.model.SQL.SiteInfo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class SiteDTO {
  private int idSite;
  private Status status;
  private String url;
  private String name;
  private String error;
  private LocalDateTime time;
  private Map<String, HashMap<Integer, String>> content;
}
