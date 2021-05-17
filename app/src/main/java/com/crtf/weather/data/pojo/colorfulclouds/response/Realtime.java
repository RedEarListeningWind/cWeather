package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * 实时
 * @author crtf
 */
@Data
public class Realtime{

	/**
	 * 状态
	 */
	@JsonProperty("status")
	private String status;

	/**
	 * 温度
	 */
	@JsonProperty("temperature")
	private double temperature;

	/**
	 * 湿度
	 */
	@JsonProperty("humidity")
	private double humidity;

	/**
	 * 云量
	 */
	@JsonProperty("cloudrate")
	private double cloudrate;

	/**
	 * 全天主要天气现象
	 */
	@JsonProperty("skycon")
	private WeatherPhenomenon skycon;

	/**
	 * 能见度
	 */
	@JsonProperty("visibility")
	private double visibility;

	/**
	 * 短波辐射
	 */
	@JsonProperty("dswrf")
	private double dswrf;

	/**
	 * 风
	 */
	@JsonProperty("wind")
	private Wind wind;

	/**
	 * 气压
	 */
	@JsonProperty("pressure")
	private double pressure;

	/**
	 * 体感温度
	 */
	@JsonProperty("apparent_temperature")
	private double apparentTemperature;

	/**
	 * 降水
	 */
	@JsonProperty("precipitation")
	private Precipitation precipitation;

	/**
	 * 空气质量
	 */
	@JsonProperty("air_quality")
	private AirQuality airQuality;

	/**
	 * 生活指数
	 */
	@JsonProperty("life_index")
	private RealtimeLifeIndex lifeIndex;


}