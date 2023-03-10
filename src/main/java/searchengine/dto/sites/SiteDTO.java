package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;
import searchengine.config.status.Status;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class SiteDTO {
  private int id;
  private String url;
  private String name;
  private List<PageDTO> pageDTOList;
}
