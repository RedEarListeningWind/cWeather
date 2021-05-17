package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;

/**
 * 每小时空气质量
 * @author crtf
 */
@Data
public class HourlyAirQuality{
    @JsonProperty("aqi")
    private List<DatetimeAndValue<Aqi>> aqi;

    /**
     * PM25浓度
     */
    @JsonProperty("pm25")
    private List<DatetimeAndValue<Double>> pm25;

}
