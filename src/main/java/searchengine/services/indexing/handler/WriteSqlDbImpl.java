package searchengine.services.indexing.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.config.site.Site;
import searchengine.config.status.Status;
import searchengine.dto.sites.IndexDTO;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.services.indexing.handler.lemmaTable.HandlerDataLemmaService;
import searchengine.services.indexing.handler.indexTable.HandlerDataIndexService;
import searchengine.services.writeDataDB.SQL.indexTable.WriteIndexTableService;
import searchengine.services.writeDataDB.SQL.lemmaTable.WriteLemmaTableService;
import searchengine.services.writeDataDB.SQL.pageTable.WritePageTableService;
import searchengine.services.writeDataDB.SQL.siteTable.WriteSiteTableService;

import java.util.*;

@Component
public class WriteSqlDbImpl implements WriteSqlDbService {
    @Autowired
    private WriteSiteTableService writeSite;
    @Autowired
    private WritePageTableService writePage;
    @Autowired
    private WriteLemmaTableService writeLemmaTableService;
    @Autowired
    private WriteIndexTableService writeIndexTableService;
    @Autowired
    private LemmaRepository lemmaRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private HandlerDataIndexService handlerDataIndex;
    @Autowired
    private HandlerDataLemmaService handlerDataLemma;

    @Override
    public void writeSiteTable(Site site) {
        writeSite.write(site);
    }

    @Override
    public SiteInfo getSiteInfo(Site site) {
        return writeSite.getSiteInfo(site);
    }

    @Override
    public void setStatus(String url, Status status, String error) {
        writeSite.setStatus(url, status, error);
    }

    @Override
    public void writePageTable(SiteDTO siteDTO) {
        writePage.write(siteDTO);
    }

    @Override
    public void writeLemmaTable(SiteInfo siteInfo, TreeMap<Integer, List<LemmaDTO>> lemmas) {
        Map<String, Integer> lemmasList = handlerDataLemma.frequencyLemmas(lemmas);
        writeLemmaTableService.write(siteInfo, lemmasList);
    }

    @Override
    public void updateLemmaTable(SiteDTO siteDTO, TreeMap<Integer, List<LemmaDTO>> lemmas) {
        List<String> lemmasListNew = handlerDataLemma.frequencyLemmas(lemmas).keySet().stream().toList();
        List<Lemma> lemmaList = lemmaRepository.getLemmaTable(siteDTO.getSiteInfo().getId());
        List<Lemma> list = handlerDataLemma.checkNewLemmas(siteDTO.getSiteInfo(), lemmasListNew, lemmaList);
        writeLemmaTableService.updateLemmaTable(list);
    }

    @Override
    public void writeIndexTable(SiteInfo siteInfo, TreeMap<Integer, List<LemmaDTO>> lemmas) {
        List<Lemma> lemmaList = lemmaRepository.getLemmaTable(siteInfo.getId());
        List<PageInfo> pageList = pageRepository.getContent(siteInfo.getId());
        List<IndexDTO> list = handlerDataIndex.createIndexDTO(lemmas, pageList, lemmaList);
        writeIndexTableService.write(list);
    }
}
