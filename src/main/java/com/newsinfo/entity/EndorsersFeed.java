package com.newsinfo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "EndorsersFeed")
@Table(name = "ENDORSERS_FEED")
public class EndorsersFeed implements Serializable {

    @Id
    private Long newsId;

    private String transactionId;

    private Integer pollCount;
}
