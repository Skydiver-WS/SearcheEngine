package searchengine.dto.sites;

import lombok.Getter;
import lombok.Setter;
import searchengine.model.sql.SiteInfo;

import java.util.List;


@Getter
@Setter
public class SiteDTO {
  private SiteInfo siteInfo;
  private List<PageDTO> pageDTOList;
}
