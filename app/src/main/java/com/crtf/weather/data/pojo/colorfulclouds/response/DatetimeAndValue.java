package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 日期时间 值
 * @author crtf
 */
@Data
public class DatetimeAndValue<T> {

    @JsonProperty("datetime")
    @JsonAlias("date")
    private Datetime datetime;

    @JsonProperty("value")
    private T value;

}
