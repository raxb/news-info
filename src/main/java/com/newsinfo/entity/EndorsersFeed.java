package com.newsinfo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "EndorsersFeed")
@Table(name = "ENDORSERS_FEED")
public class EndorsersFeed implements Serializable {

    @Id
    private Long newsId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newsId")
    @MapsId
    @JsonManagedReference
    private NewsInitializer newsInitializer;

    private Integer pollCount;
}
