package searchengine.services.indexing.core.find;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.dto.sites.PageDTO;

import searchengine.model.sql.PageInfo;
import searchengine.model.sql.SiteInfo;
import searchengine.repository.sql.PageRepository;
import searchengine.repository.sql.SiteRepository;


import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Component
public class FindElementImpl implements FindElementService {
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private PageRepository pageRepository;

    @Override
    public PageDTO find(String url) {
        String[] convertUrl = convertUrl(url);
        String urlSite = convertUrl[0];
        String path = convertUrl[1];
        Optional<SiteInfo> siteInfo = siteRepository.getSiteInfo(urlSite);
        Integer siteId = siteInfo.orElse(null).getId();
        Optional<PageInfo> page = pageRepository.findPage(siteId, path);
        PageDTO pageDTO = new PageDTO();
        if (page.isPresent()) {
            PageInfo pageInfo = page.get();
            pageDTO.setId(pageInfo.getId());
            pageDTO.setSiteInfo(pageInfo.getSiteId());
            pageDTO.setUrl(url);
            return pageDTO;
        } else {
            List<SiteInfo> list = siteRepository.findAll();
            return list.stream()
                    .filter(site -> Pattern.compile(site.getUrl())
                            .matcher(url).find())
                    .map(site -> {
                        pageDTO.setUrl(url);
                        pageDTO.setSiteInfo(site);
                        return pageDTO;
                    })
                    .findFirst()
                    .orElse(null);
        }
    }

    private String [] convertUrl(String url) {
        String [] result = new String[0];
        char ch = '/';
        int index = url.indexOf(ch, 8);
        if (index == -1){
            url = url + "/";
            result = convertUrl(url);
        } else {
            String part1 = url.substring(0, index);
            String part2 = url.substring(index);
            result = new String[]{part1, part2};
        }
        return result;
    }
}
