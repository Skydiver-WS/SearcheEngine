package searchengine.services.writeDataInDB.SQL;

import org.springframework.stereotype.Service;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.PageDTO;

import java.util.List;

@Service
public class WriteSqlDbImpl implements WriteSqlDbService{
  @Override
  public void writeSiteTable() {
//    siteDTO = writeSite.write(siteDTO);
//    cashStatistics.setSiteStatistics(siteDTO);
  }

  @Override
  public void writePageTable(List<PageDTO> pageDTOList) {

  }

  @Override
  public void writeLemmaTable(List<LemmaDTO> lemmaDTOList) {

  }
}
