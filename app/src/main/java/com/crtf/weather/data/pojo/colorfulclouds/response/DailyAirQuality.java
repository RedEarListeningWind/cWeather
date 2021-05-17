package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * 每日空气质量
 * @author crtf
 */
@Data
public class DailyAirQuality {

    @JsonProperty("aqi")
    private List<DatetimeAndMaxAndMinAndAvg<Aqi>> aqi;

    @JsonProperty("pm25")
    private List<DatetimeAndMaxAndMinAndAvg<Double>> pm25;
}
