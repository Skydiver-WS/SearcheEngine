package searchengine.services.indexing.core.find;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.dto.sites.PageDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.repository.SQL.PageRepository;

import java.util.Optional;
@Component
public class FindElementImpl implements FindElementService{
    @Autowired
    private PageRepository pageRepository;
    @Override
    public PageDTO find(String url) {
        Optional<PageInfo> page = pageRepository.findPage(url);
        PageDTO pageDTO = new PageDTO();
        if (page.isPresent()){
            PageInfo pageInfo = page.get();
            pageDTO.setId(pageInfo.getId());
            pageDTO.setSiteInfo(pageInfo.getSiteId());
        }
        pageDTO.setUrl(url);
        return pageDTO;
    }
}
