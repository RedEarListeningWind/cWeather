package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * 分钟级部分的天气变量
 */
@Data
public class Minutely{

	/**
	 * 状态
	 */
	@JsonProperty("status")
	private String status;

	/**
	 * 数据源
	 */
	@JsonProperty("datasource")
	private String datasource;

	/**
	 * 两小时降水强度
	 */
	@JsonProperty("precipitation_2h")
	private List<Double> precipitation2h;

	/**
	 * 一小时降水强度
	 */
	@JsonProperty("precipitation")
	private List<Double> precipitation;

	/**
     * 降水概率
	 */
	@JsonProperty("probability")
	private List<Double> probability;

	/**
     * 逐小时预报自然语言描述
	 */
	@JsonProperty("description")
	private String description;


}