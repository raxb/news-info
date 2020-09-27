package com.newsinfo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewsRequest {

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("location")
    private String eventLocation;

    @JsonProperty("reporterId")
    private String newsInfoIdentifier;

    @JsonIgnore
    private String images;
}
