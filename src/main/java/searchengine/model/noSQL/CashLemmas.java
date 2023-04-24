package searchengine.model.noSQL;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import searchengine.model.SQL.SiteInfo;

@RedisHash("Lemmas")
@Getter
@Setter
public class CashLemmas {
    int id;
    int frequency;
    String lemma;
    SiteInfo siteInfo;
}
