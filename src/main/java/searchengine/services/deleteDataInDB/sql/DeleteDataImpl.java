package searchengine.services.deleteDataInDB.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.site.Site;
import searchengine.config.status.Status;
import searchengine.model.SQL.Index;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.IndexRepository;
import searchengine.repository.SQL.LemmaRepository;
import searchengine.repository.SQL.PageRepository;
import searchengine.repository.SQL.SiteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeleteDataImpl implements DeleteDataService {
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private PageRepository pageRepository;

    @Override
    public void delete(Site site) {
        synchronized (siteRepository){
            siteRepository.delete(site.getUrl());
            siteRepository.garbageClear(site.getUrl());
        }
    }

    @Override
    public void delete(int id) {
        pageRepository.delete(id);
    }
}
