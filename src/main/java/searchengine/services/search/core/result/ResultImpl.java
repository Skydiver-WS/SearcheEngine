package searchengine.services.search.core.result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.dto.search.RelevanceDTO;
import searchengine.dto.search.ResultDTO;
import searchengine.model.SQL.PageInfo;
import searchengine.repository.SQL.PageRepository;
import searchengine.services.search.core.result.snippet.SnippetService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class ResultImpl implements ResultService{
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SnippetService snippetService;
    @Override
    public ResultDTO[] getResult(RelevanceDTO[] dto) {
        List<Integer> listId = new ArrayList<>();
        Arrays.stream(dto).map(RelevanceDTO::getPageId).forEach(listId :: add);
        List<PageInfo> pageInfoList = pageRepository.findAllById(listId);
        return Arrays.stream(dto).map(o -> {
            PageInfo pageInfo = pageInfoList.stream().filter(id -> o.getPageId() == id.getId()).findFirst().orElse(null);
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setSite(pageInfo.getSiteId().getUrl());
            resultDTO.setSiteName(pageInfo.getSiteId().getName());
            resultDTO.setUri(pageInfo.getPath());
            resultDTO.setRelevance(o.getRelRelevance());
            resultDTO.setTitle(getTitlePage(pageInfo));
            resultDTO.setSnippet(snippetService.getSnippet(pageInfo, o.getFrequencyLemmaDTOList()));
            return resultDTO.getSnippet().length() == 0 ? null : resultDTO;
        }).toArray(ResultDTO[]::new);
    }
    private String getTitlePage(PageInfo pageInfo){
        Document doc = Jsoup.parse(pageInfo.getContent());
        return doc.title();
    }
}
