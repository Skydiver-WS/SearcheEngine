package searchengine.model.nosql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("Lemmas")
@Getter
@Setter
public class CashLemmas implements Serializable {
    @Id
    private int id;
    @Indexed
    private String lemma;
    private int frequency;
    private int lemmaId;
    private float rank;
    private int pageId;
    private int siteId;
}
