package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * 结果
 * @author crtf
 */
@Data
public class Result{

	/**
	 * 预警信息
	 */
	@JsonProperty("alert")
	private Alert alert;

	/**
	 * 即时
	 */
	@JsonProperty("realtime")
	private Realtime realtime;


	/**
	 * 分钟级部分的天气
	 */
	@JsonProperty("minutely")
	private Minutely minutely;

	/**
	 * 小时级部分的天气变量
	 */
	@JsonProperty("hourly")
	private Hourly hourly;

	/**
	 * 逐日预报
	 */
	@JsonProperty("daily")
	private Daily daily;

	/**
	 * 主要
	 */
	@JsonProperty("primary")
	private int primary;

	/**
	 * 综合预报自然语言描述
	 */
	@JsonProperty("forecast_keypoint")
	private String forecastKeypoint;

}