package searchengine.services.writeDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.Status;
import searchengine.dto.sites.SiteDTO;
import searchengine.model.PageInfo;
import searchengine.model.SiteInfo;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WritePageTable implements WritePageDBService {
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SiteInfo siteInfo;

    @Override
    public synchronized void write(SiteDTO siteDTO) {
        ArrayList <PageInfo> list = new ArrayList<>();
        for (int i = 0; i < siteDTO.getContent().size(); i++) {
            PageInfo pageInfo = new PageInfo();
            String key = key(siteDTO)[i].toString();
            int code = responseCode(siteDTO, key);
            String content = content(siteDTO, key, code);
            pageInfo.setId(0);
            pageInfo.setSiteId(site(siteDTO));
            pageInfo.setPath(key);
            pageInfo.setCode(code);
            pageInfo.setContent(content);
            list.add(pageInfo);
        }
        pageRepository.saveAllAndFlush(list);
    }

    private SiteInfo site(SiteDTO siteDTO) {
        Optional<SiteInfo> site = siteRepository.findById(siteDTO.getIdSite());
        return site.orElse(null);
    }

    private Object[] key(SiteDTO dto) {
        return dto.getContent().keySet().toArray();
    }

    private int responseCode(SiteDTO dto, String key) {
        Object[] code = dto.getContent().get(key).keySet().toArray();
        return Integer.parseInt(code[0].toString());
    }

    private String content(SiteDTO dto, String key, Integer responseCode) {
        return dto.getContent().get(key).get(responseCode);
    }
}
