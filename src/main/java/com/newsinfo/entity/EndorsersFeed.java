package com.newsinfo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "EndorsersFeed")
@Table(name = "ENDORSERS_FEED")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class EndorsersFeed implements Serializable {

    @Id
    private Long newsId;

    private String transactionId;

    private Integer pollCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EndorsersFeed)) return false;
        EndorsersFeed that = (EndorsersFeed) o;
        return getNewsId() != null && getNewsId().equals(that.getNewsId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNewsId());
    }
}
