package searchengine.services.deletedatadb.nosql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import searchengine.repository.sql.IndexRepository;
import searchengine.repository.nosql.CashLemmasRepository;

import java.util.List;

@Component
public class DeleteCashLemmasImpl implements DeleteCashLemmasService{
    @Autowired
    private IndexRepository indexRepository;
    @Autowired
    private CashLemmasRepository cashLemmasRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public void delete(){
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushDb();
            return null;
        });
    }
    @Override
    public void delete(Integer pageId) {
        List<Integer> listIndexId = indexRepository.getIndexId(pageId);
        listIndexId.forEach(id -> cashLemmasRepository.deleteById(Math.toIntExact(id)));
    }
}
