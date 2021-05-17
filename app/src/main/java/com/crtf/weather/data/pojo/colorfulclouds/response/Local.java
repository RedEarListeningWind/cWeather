package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * 本地
 */
@Data
public class Local{

	/**
     * 降水强度
	 */
	@JsonProperty("intensity")
	private double intensity;

	/**
     * 数据源
	 */
	@JsonProperty("datasource")
	private String datasource;

	/**
     * 状态
	 */
	@JsonProperty("status")
	private String status;


}