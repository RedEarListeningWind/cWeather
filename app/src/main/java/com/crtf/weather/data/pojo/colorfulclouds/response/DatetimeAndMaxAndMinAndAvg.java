package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * 日期时间 最大 最小 平均
 * @author crtf
 */
@Data
public class DatetimeAndMaxAndMinAndAvg<T> {
    /**
     * 日期时间
     */
    @JsonProperty("date")
    private Datetime date;

    /**
     * 最大
     */
    @JsonProperty("max")
    private T max;

    /**
     * 最小
     */
    @JsonProperty("min")
    private T min;

    /**
     * 平均
     */
    @JsonProperty("avg")
    private T avg;

}
