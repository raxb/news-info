package com.newsinfo.entity;

import com.newsinfo.utils.generator.StringPrefixedSequenceIdGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "EndorserProfile")
@Table(name = "ENDORSER_PROFILE")
public class EndorserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @GenericGenerator(
            name = "profile_seq",
            strategy = "com.newsinfo.utils.generator.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM,
                            value = "1"),
                    @org.hibernate.annotations.Parameter(name =
                            StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "NEN_"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})
    private String id;
    private String firstName;
    private String lastName;
    private String location;
    private Integer points;
}
