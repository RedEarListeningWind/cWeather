package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * 根元素
 * @author crtf
 */
@Data
public class ResponseColorfulClouds {

	/**
	 * 结果
	 */
	@JsonProperty("result")
	private Result result;

	/**
	 * 服务器时间
	 */
	@JsonProperty("server_time")
	private int serverTime;

	/**
	 * 单位
	 */
	@JsonProperty("unit")
	private String unit;

	/**
	 * 时区
	 */
	@JsonProperty("timezone")
	private String timezone;
	/**
	 * api 状态
	 */
	@JsonProperty("api_status")
	private String apiStatus;

	/**
	 * 位置
	 */
	@JsonProperty("location")
	private List<Double> location;

	/**
	 * tz 转移
	 */
	@JsonProperty("tzshift")
	private int tzshift;

	/**
	 * api 版本
	 */
	@JsonProperty("api_version")
	private String apiVersion;

	/**
	 * 语言
	 */
	@JsonProperty("lang")
	private String lang;

	/**
	 * 状态
	 */
	@JsonProperty("status")
	private String status;


}