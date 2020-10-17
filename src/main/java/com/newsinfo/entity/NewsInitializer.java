package com.newsinfo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.newsinfo.dto.NewsEndorserFeedDAO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * JPA Entity for CRUD operations
 */
@Getter
@Setter
@Entity(name = "NewsInitializer")
@Table(name = "NEWS_INITIALIZER")
@NamedNativeQuery(
        name = "NewsInitializer.NewsEndorserFeedDataQuery",
        query = "select ni.news_id,ni.images,ni.location,ni.topic,ni.transaction_date,ni.transaction_time," +
                "ef.poll_count" +
                " from " +
                "news_info.news_initializer ni, news_info.endorsers_feed ef where " +
                "ni.news_Id = ef.news_Id and " +
                "((str_to_date(ni.transaction_time,'%H:%i:%s') < (select subtime(now(),'00:15:00')) and date" +
                "(ni.transaction_date) = date(now())) or date(ni.transaction_date) < date(now())) order by " +
                "ni.transaction_date desc, ni.transaction_time desc",
        resultSetMapping = "newsEndorserFeedMapper"
)
@SqlResultSetMapping(
        name = "newsEndorserFeedMapper",
        classes = {
                @ConstructorResult(
                        targetClass = NewsEndorserFeedDAO.class,
                        columns = {
                                @ColumnResult(name = "ni.news_id", type = long.class),
                                @ColumnResult(name = "ni.images", type = String.class),
                                @ColumnResult(name = "ni.location", type = String.class),
                                @ColumnResult(name = "ni.topic", type = String.class),
                                @ColumnResult(name = "ni.transaction_date", type = String.class),
                                @ColumnResult(name = "ni.transaction_time", type = String.class),
                                @ColumnResult(name = "ef.poll_count", type = int.class)
                        }
                )
        }
)
public class NewsInitializer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long newsId;
    private String transactionId;
    private String topic;
    private String location;
    private String images;
    private String transactionDate;
    private String transactionTime;
    private boolean isUpdated;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private EndorsersFeed endorsersFeed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private ReporterProfile reporterProfile;

}
