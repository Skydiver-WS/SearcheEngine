package searchengine.services.indexing.checkIndexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.status.Status;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.SiteRepository;
import searchengine.services.indexing.IndexingImpl;

import java.util.ArrayList;
import java.util.List;

//TODO: как доведу до ума обновление данных из Redis перевести работу на Redis репозиторий
@Service
public class ChangeStartIndexingImpl implements ChangeStartIndexingService{
    @Autowired
    private SiteRepository repository;


    @Override
    public boolean change() {
        List<SiteInfo> list = repository.findAll();
        if(IndexingImpl.isAliveThread()){
            for (SiteInfo site:list) {
                Status status = site.getStatus();
                if(Status.INDEXING.equals(status)){
                    return true;
                }
            }
        }
        return false;
    }
}
