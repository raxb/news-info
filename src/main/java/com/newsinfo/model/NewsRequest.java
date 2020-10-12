package com.newsinfo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Request schema definition
 */
@Data
public class NewsRequest {

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("location")
    private String eventLocation;

    @JsonProperty("reporterId")
    private String reporterId;

    @JsonProperty("images")
    private String images;
}
