package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * 空气质量
 */
@Data
public class AirQuality{

	/**
	 * PM25浓度
	 */
	@JsonProperty("pm25")
	private double pm25;

	/**
	 * PM10浓度
	 */
	@JsonProperty("pm10")
	private double pm10;

	/**
	 * 臭氧浓度
	 */
	@JsonProperty("o3")
	private double o3;

	/**
	 * 二氧化硫浓度
	 */
	@JsonProperty("so2")
	private double so2;

	/**
	 * 二氧化氮浓度
	 */
	@JsonProperty("no2")
	private double no2;

	/**
	 * 一氧化碳浓度
	 */
	@JsonProperty("co")
	private double co;

	@JsonProperty("aqi")
	private Aqi aqi;

	/**
	 * 描述
	 */
	@JsonProperty("description")
	private Description description;


}