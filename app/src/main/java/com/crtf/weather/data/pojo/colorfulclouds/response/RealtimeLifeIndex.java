package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * 实时生活指数
 * @author crtf
 */
@Data
public class RealtimeLifeIndex {

    @JsonProperty("ultraviolet")
    private IndexAndDesc ultraviolet;

    @JsonProperty("comfort")
    private IndexAndDesc comfort;

}
