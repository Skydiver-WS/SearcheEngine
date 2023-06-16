package searchengine.services.writedatadb.nossql;

import java.util.List;

public interface CashLemmasService {
    void writeLemmas(List<String> listLemmas);
    void writeLemmas(int pageId);
}
