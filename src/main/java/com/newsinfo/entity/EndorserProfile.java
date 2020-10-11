package com.newsinfo.entity;

import com.newsinfo.utils.generator.StringPrefixedSequenceIdGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "EndorserProfile")
@Table(name = "ENDORSER_PROFILE")
public class EndorserProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @GenericGenerator(
            name = "profile_seq",
            strategy = "com.newsinfo.utils.generator.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM,
                            value = "1"),
                    @Parameter(name =
                            StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "NEN_"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})
    @Column(name = "profile_id")
    private String id;
    private String firstName;
    private String lastName;
    private String location;
    private Integer points;

    @OneToMany(mappedBy = "endorserProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<PolledEndorsedNews> polledEndorsedNewsSet = new HashSet<>();

    public void addPolledEndorsedNews(PolledEndorsedNews polledEndorsedNews) {
        polledEndorsedNewsSet.add(polledEndorsedNews);
        polledEndorsedNews.setEndorserProfile(this);
    }

    public void removePolledEndorsedNews(PolledEndorsedNews polledEndorsedNews) {
        polledEndorsedNewsSet.remove(polledEndorsedNews);
        polledEndorsedNews.setEndorserProfile(null);
    }
}
