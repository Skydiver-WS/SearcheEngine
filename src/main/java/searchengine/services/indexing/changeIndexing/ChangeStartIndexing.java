package searchengine.services.indexing.changeIndexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.status.Status;
import searchengine.model.SQL.SiteInfo;
import searchengine.repository.SQL.SiteRepository;

import java.util.ArrayList;
import java.util.List;

//TODO: как доведу до ума обновление данных из Redis перевести работу на Redis репозиторий
@Service
public class ChangeStartIndexing implements ChangeStartIndexingService{
    @Autowired
    private SiteRepository repository;


    @Override
    public boolean change(ArrayList<Thread> threadList) {
        List<SiteInfo> list = repository.findAll();
        if(threadList.size() > 0){
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
