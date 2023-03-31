package searchengine.services.indexing.core.find;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.dto.sites.PageDTO;

import searchengine.model.SQL.PageInfo;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;


import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class FindElementImpl implements FindElementService {
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private IndexRepository indexRepository;

    @Override
    public PageDTO find(String url) {
        Optional<PageInfo> page = pageRepository.findPage(url);
        PageDTO pageDTO = new PageDTO();
        if (page.isPresent()) {
            PageInfo pageInfo = page.get();
            pageDTO.setId(pageInfo.getId());
            pageDTO.setSiteInfo(pageInfo.getSiteId());
            pageDTO.setUrl(url);
            return pageDTO;
        }else {
//            List<SiteInfo> list = siteRepository.findAll();
//            for (SiteInfo site:list) {
//                Pattern pattern = Pattern.compile(site.getUrl());
//                Matcher matcher = pattern.matcher(url);
//                if(matcher.find()){
//                    pageDTO.setUrl(url);
//                    pageDTO.setSiteInfo(site);
//                }
//            }
//            return pageDTO;
            List<SiteInfo> list = siteRepository.findAll();
            return list.stream()
                    .filter(site -> Pattern.compile(site.getUrl()).matcher(url).find())
                    .map(site -> {
                        pageDTO.setUrl(url);
                        pageDTO.setSiteInfo(site);
                        return pageDTO;
                    })
                    .findFirst()
                    .orElse(null);
        }
    }
}
