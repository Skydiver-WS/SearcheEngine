package searchengine.services.search.core.result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.dto.search.RelevanceDTO;
import searchengine.dto.search.ResultDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.repository.SQL.PageRepository;
import searchengine.services.search.core.snippet.SnippetService;

import java.util.Arrays;


@Component
public class ResultImpl implements ResultService{
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SnippetService snippetService;
    @Override
    public ResultDTO[] getResult(RelevanceDTO[] dto) {
        return Arrays.stream(dto).map(o -> {
            PageInfo pageInfo = pageRepository.getPageInfo(o.getPageId());
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setSite(pageInfo.getSiteId().getUrl());
            resultDTO.setSiteName(pageInfo.getSiteId().getName());
            resultDTO.setUri(pageInfo.getPath());
            resultDTO.setRelevance(o.getRelRelevance());
            resultDTO.setTitle(getTitlePage(pageInfo));
            resultDTO.setSnippet(snippetService.getSnippet(pageInfo, o.getFrequencyLemmaDTOList()));
            return resultDTO;
        }).toArray(ResultDTO[]::new);
    }
    private String getTitlePage(PageInfo pageInfo){
        Document doc = Jsoup.parse(pageInfo.getContent());
        return doc.title();
    }
}
