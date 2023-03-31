package searchengine.services.indexing.singleIndexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.status.Status;
import searchengine.dto.sites.LemmaDTO;
import searchengine.dto.sites.PageDTO;
import searchengine.dto.sites.SiteDTO;
import searchengine.services.deleteDataDB.sql.DeleteDataService;
import searchengine.services.indexing.core.check.url.CheckUrlService;
import searchengine.services.indexing.core.find.FindElementService;
import searchengine.services.indexing.core.lemma.LemmaService;
import searchengine.services.indexing.core.parse.ParseService;
import searchengine.services.indexing.handler.WriteSqlDbService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Service
public class IndexPageImpl implements IndexPageService {
    @Autowired
    private CheckUrlService checkUrl;
    @Autowired
    private FindElementService findElementService;
    @Autowired
    private ParseService parseService;
    @Autowired
    private WriteSqlDbService writeSqlDbService;
    @Autowired
    private LemmaService lemmaService;
    @Autowired
    private DeleteDataService deleteDataService;
    private final SiteDTO siteDTO = new SiteDTO();
//TODO: добавить обработчик, при отсутсвии необходимой страницы
    @Override
    public HashMap<String, Object> indexPage(String url) {
        //TODO: сделать запуск разных ссылок в разных потоках
        HashMap<String, Object> response = new HashMap<>();
        if (checkUrl.check(url)) {
            List<PageDTO> list = new ArrayList<>();
            PageDTO pageDTO = findElementService.find(url);
            parseService.parsePage(pageDTO);
            list.add(pageDTO);
            siteDTO.setPageDTOList(list);
            siteDTO.setSiteInfo(pageDTO.getSiteInfo());
            writeSqlDbService.setStatus(siteDTO.getSiteInfo().getUrl(), Status.INDEXING, null);//TODO при siteInfo = null вылетает ошибка
            deleteDataService.delete(siteDTO);
            writeSqlDbService.writePageTable(siteDTO);
            TreeMap<Integer, List<LemmaDTO>> lemmas = lemmaService.getListLemmas(siteDTO.getPageDTOList());
            writeSqlDbService.updateLemmaTable(siteDTO, lemmas);
            writeSqlDbService.writeIndexTable(siteDTO.getSiteInfo(), lemmas);
            writeSqlDbService.setStatus(siteDTO.getSiteInfo().getUrl(), Status.INDEXED, null);
            response.put("result", true);
        } else {
            response.put("result", false);
            response.put("error", "Данная страница находится за пределами сайтов, " +
                    "указанных в конфигурационном файле.");
        }
        return response;
    }
}
