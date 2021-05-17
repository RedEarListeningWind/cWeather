package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.crtf.weather.data.pojo.colorfulclouds.response.converter.CustomHourlyWindConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * 小时级部分的天气变量
 * @author crtf
 */
@Data
public class Hourly{

	/**
	 * 状态
	 */
	@JsonProperty("status")
	private String status;

	/**
	 * 逐小时预报自然语言描述
	 */
	@JsonProperty("description")
	private String description;

	/**
	 * 降水
	 */
	@JsonProperty("precipitation")
	private List<DatetimeAndValue<Double>> precipitation;

	/**
	 * 温度
	 */
	@JsonProperty("temperature")
	private List<DatetimeAndValue<Double>> temperature;

	/**
	 * 风
	 */
	@JsonProperty("wind")
//	@JsonDeserialize(contentUsing = CustomWindDeserializer.class,contentConverter = MyConverter.class)
	@JsonDeserialize(converter = CustomHourlyWindConverter.class)
	private List<DatetimeAndValue<Wind>> wind;

	/**
	 * 相对湿度
	 */
	@JsonProperty("humidity")
	private List<DatetimeAndValue<Double>> humidity;

	/**
	 * 云量
	 */
	@JsonProperty("cloudrate")
	private List<DatetimeAndValue<Double>> cloudrate;

	/**
	 * 主要天气现象
	 */
	@JsonProperty("skycon")
	private List<DatetimeAndValue<WeatherPhenomenon>> skycon;

	/**
	 * 气压
	 */
	@JsonProperty("pressure")
	private List<DatetimeAndValue<Double>> pressure;

	/**
	 * 能见度
	 */
	@JsonProperty("visibility")
	private List<DatetimeAndValue<Double>> visibility;

	/**
	 * 短波辐射
	 */
	@JsonProperty("dswrf")
	private List<DatetimeAndValue<Double>> dswrf;

	/**
	 * 空气质量
	 */
	@JsonProperty("air_quality")
	private HourlyAirQuality airQuality;

}