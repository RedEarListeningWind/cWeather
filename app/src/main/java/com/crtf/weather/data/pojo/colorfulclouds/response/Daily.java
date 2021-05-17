package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.crtf.weather.data.pojo.colorfulclouds.response.converter.CustomDailyAstroConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * 天级部分的天气
 * @author crtf
 */
@Data
public class Daily{

	/**
	 * 状态
	 */
	@JsonProperty("status")
	private String status;

	/**
	 * 日出日落
	 */
	@JsonProperty("astro")
	@JsonDeserialize(converter = CustomDailyAstroConverter.class)
	private List<DatetimeAndValue<Astro>> astro;

	/**
	 * 降水
	 */
	@JsonProperty("precipitation")
	private List<DatetimeAndMaxAndMinAndAvg<Double>> precipitation;

	/**
	 * 温度
	 */
	@JsonProperty("temperature")
	private List<DatetimeAndMaxAndMinAndAvg<Double>> temperature;

	/**
	 * 风
	 */
	@JsonProperty("wind")
	private List<DatetimeAndMaxAndMinAndAvg<Wind>> wind;

	/**
	 * 相对湿度
	 */
	@JsonProperty("humidity")
	private List<DatetimeAndMaxAndMinAndAvg<Double>> humidity;

	/**
	 * 云量
	 */
	@JsonProperty("cloudrate")
	private List<DatetimeAndMaxAndMinAndAvg<Double>> cloudrate;

	/**
	 * 压强
	 */
	@JsonProperty("pressure")
	private List<DatetimeAndMaxAndMinAndAvg<Double>> pressure;

	/**
	 * 能见度
	 */
	@JsonProperty("visibility")
	private List<DatetimeAndMaxAndMinAndAvg<Double>> visibility;

	/**
	 * 短波辐射
	 */
	@JsonProperty("dswrf")
	private List<DatetimeAndMaxAndMinAndAvg<Double>> dswrf;

	/**
	 * 空气质量
	 */
	@JsonProperty("air_quality")
	private DailyAirQuality airQuality;

	/**
	 * 全天主要天气现象
	 */
	@JsonProperty("skycon")
	private List<DatetimeAndValue<WeatherPhenomenon>> skycon;



	/**
	 * 白天主要天气现象
	 */
	@JsonProperty("skycon_08h_20h")
	private List<DatetimeAndValue<WeatherPhenomenon>> skycon08h20h;



	/**
	 * 夜晚主要天气现象
	 */
	@JsonProperty("skycon_20h_32h")
	private List<DatetimeAndValue<WeatherPhenomenon>> skycon20h32h;




	/**
	 * 生活指数
	 */
	@JsonProperty("life_index")
	private DailyLivingIndex lifeIndex;


}