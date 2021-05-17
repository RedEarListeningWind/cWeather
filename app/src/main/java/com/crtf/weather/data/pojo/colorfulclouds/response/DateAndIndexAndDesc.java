package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * date index desc
 * @author crtf
 */
@Data
public class DateAndIndexAndDesc {

    @JsonProperty("date")
    private Datetime date;

    @JsonProperty("index")
    private double index;

    @JsonProperty("desc")
    private String desc;
}
