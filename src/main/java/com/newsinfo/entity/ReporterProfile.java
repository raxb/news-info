package com.newsinfo.entity;

import com.newsinfo.utils.generator.StringPrefixedSequenceIdGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "ReporterProfile")
@Table(name = "REPORTER_PROFILE")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ReporterProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @GenericGenerator(
            name = "profile_seq",
            strategy = "com.newsinfo.utils.generator.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM,
                            value = "1"),
                    @Parameter(name =
                            StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "REP_"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})
    @Column(name = "reporter_id")
    private String id;
    private String firstName;
    private String lastName;
    private String location;
    private Integer points;

    @OneToMany(mappedBy = "reporterProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<NewsInitializer> newsInitializerSet = new HashSet<>();

    public void addNewsInitializer(NewsInitializer newsInitializer) {
        newsInitializerSet.add(newsInitializer);
        newsInitializer.setReporterProfile(this);
    }

    public void removeNewsInitializer(NewsInitializer newsInitializer) {
        newsInitializerSet.remove(newsInitializer);
        newsInitializer.setReporterProfile(null);
    }

}
