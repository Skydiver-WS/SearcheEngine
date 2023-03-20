package searchengine.services.writeDataDB.SQL.indexTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.sites.IndexDTO;
import searchengine.model.SQL.Index;
import searchengine.repository.SQL.IndexRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class WriteIndexTableImpl implements WriteIndexTableService {
    @Autowired
    private IndexRepository indexRepository;

    @Override
    public void write(List<IndexDTO> listIndexDTO) {
        List<Index> list = new ArrayList<>();
        for (IndexDTO indexDTO : listIndexDTO) {
            Index index = new Index();
            index.setLemmaId(indexDTO.getLemma());
            index.setPageId(indexDTO.getPageInfo());
            index.setRank(indexDTO.getRank());
            list.add(index);
        }
        synchronized (indexRepository) {
            indexRepository.saveAll(list);
        }
    }
}
