package searchengine.services.writeDataInDB.SQL.indexTable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import searchengine.dto.sites.IndexDTO;
import searchengine.dto.sites.LemmaDTO;
import searchengine.model.SQL.Index;
import searchengine.model.SQL.SiteInfo;

import java.util.List;
import java.util.Set;

public interface WriteIndexTableService {
  void write(List <IndexDTO> listIndexDTO);
}
