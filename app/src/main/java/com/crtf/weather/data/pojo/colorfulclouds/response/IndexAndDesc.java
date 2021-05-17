package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;


/**
 * 指数 和 描述
 * @author crtf
 */
@Data
public class IndexAndDesc {
    @JsonProperty("index")
    private double index;

    @JsonProperty("desc")
    private String desc;
}
