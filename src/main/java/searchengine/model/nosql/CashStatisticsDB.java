package searchengine.model.nosql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("CashStatistics")
@Getter
@Setter
public class CashStatisticsDB {
    private int id;
    private String url;
    private String name;
    private String status;
    private LocalDateTime statusTime;
    private String error;
    private int pages;
    private int lemmas;
}
