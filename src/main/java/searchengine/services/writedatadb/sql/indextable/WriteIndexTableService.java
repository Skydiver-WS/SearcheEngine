package searchengine.services.writedatadb.sql.indextable;

import searchengine.dto.sites.IndexDTO;

import java.util.List;

public interface WriteIndexTableService {
  void write(List <IndexDTO> listIndexDTO);
}
