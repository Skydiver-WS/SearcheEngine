package searchengine.services.writeDataInDB.SQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.config.site.Site;
import searchengine.dto.sites.IndexDTO;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.SQL.Index;
import searchengine.model.SQL.Lemma;
import searchengine.model.SQL.PageInfo;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.services.writeDataInDB.SQL.indexTable.WriteIndexTableService;
import searchengine.services.writeDataInDB.SQL.lemmaTable.WriteLemmaTableService;
import searchengine.services.writeDataInDB.SQL.pageTable.WritePageTableService;
import searchengine.services.writeDataInDB.SQL.siteTable.WriteSiteTableService;

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

    @Override
    public void writeSiteTable(Site site) {
        writeSite.write(site);
    }

    @Override
    public SiteInfo getSiteInfo(Site site) {
        return writeSite.getSiteInfo(site);
    }


    @Override
    public void writePageTable(SiteDTO siteDTO) {
        writePage.write(siteDTO);
    }

    @Override
    public void writeLemmaTable(SiteInfo siteInfo, TreeMap<Integer, List<LemmaDTO>> lemmas) {
        Map<String, Integer> lemmasList = frequencyLemmas(lemmas);
        writeLemmaTableService.write(siteInfo, lemmasList);
    }

    @Override
    public void writeIndexTable(SiteInfo siteInfo, TreeMap<Integer, List<LemmaDTO>> lemmas) {
        List<Lemma> lemmaList = lemmaRepository.getLemmaTable(siteInfo.getId());
        List<PageInfo> pagesList = pageRepository.getContent(siteInfo.getId());

    }

    private Map<String, Integer> frequencyLemmas(TreeMap<Integer, List<LemmaDTO>> listLemmas){
        HashSet<LemmaDTO> list = new HashSet<>();
        Map<String, Integer> lemmas = new TreeMap<>();
        for (Integer key: listLemmas.keySet()) {
            list.addAll(listLemmas.get(key));
        }
        for (LemmaDTO lemmaDTO: list){
            if (!lemmas.containsKey(lemmaDTO.getLemma())){
                lemmas.put(lemmaDTO.getLemma(), 1);
            } else {
                int count = lemmas.get(lemmaDTO.getLemma()) + 1;
                lemmas.put(lemmaDTO.getLemma(), count);
            }
        }
        return lemmas;
    }

    private List<IndexDTO> createIndexDTO(List<PageInfo> pagesList, TreeMap<Integer, List<LemmaDTO>> lemmas, SiteInfo siteInfo){
        int siteId = siteInfo.getId();
        for (Integer key: lemmas.keySet()) {

        }

        return null;
    }
    private Lemma getLemma(List<LemmaDTO> lemmaDTOList, int siteId){

        return null;
    }


}
