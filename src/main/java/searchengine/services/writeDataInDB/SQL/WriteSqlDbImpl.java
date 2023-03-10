package searchengine.services.writeDataInDB.SQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.writeDataInDB.SQL.pageTable.WritePageTableService;
import searchengine.services.writeDataInDB.SQL.siteTable.WriteSiteTableService;

import java.util.List;

@Component
public class WriteSqlDbImpl implements WriteSqlDbService {
    @Autowired
    private WriteSiteTableService writeSite;
    @Autowired
    private WritePageTableService writePage;

    @Override
    public void writeSiteTable(SiteDTO siteDTO) {
        writeSite.write(siteDTO);
    }

    @Override
    public void writePageTable(SiteDTO siteDTO) {
        writePage.write(siteDTO);
    }

    @Override
    public void writeLemmaTable(List<LemmaDTO> lemmaDTOList) {

    }
}
