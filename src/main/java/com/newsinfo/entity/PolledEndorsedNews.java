package com.newsinfo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "PolledEndorsedNews")
@Table(name = "POLLED_ENDORSED_NEWS")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class PolledEndorsedNews implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;
    private String newsId;
    private String polledDateTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private EndorserProfile endorserProfile;

    public PolledEndorsedNews() {

    }

    public PolledEndorsedNews(String newsId) {
        this.newsId = newsId;
        polledDateTimestamp = java.time.LocalDateTime.now().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PolledEndorsedNews)) return false;
        PolledEndorsedNews that = (PolledEndorsedNews) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
