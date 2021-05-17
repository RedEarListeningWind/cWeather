package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * 每日生活指数
 * @author crtf
 */
@Data
public class DailyLivingIndex{

	/**
	 * 生活指数等级 紫外线
	 */
	@JsonProperty("ultraviolet")
	private List<DateAndIndexAndDesc> ultraviolet;

	/**
	 * 穿衣指数
	 */
	@JsonProperty("dressing")
	private List<DateAndIndexAndDesc> dressing;


	/**
     * 生活指数等级 感冒
	 */
	@JsonProperty("coldRisk")
	private List<DateAndIndexAndDesc> coldRisk;

	/**
     * 洗车指数
	 */
	@JsonProperty("carWashing")
	private List<DateAndIndexAndDesc> carWashing;

	/**
     * 舒适度指数
	 */
	@JsonProperty("comfort")
	private List<DateAndIndexAndDesc> comfort;


}