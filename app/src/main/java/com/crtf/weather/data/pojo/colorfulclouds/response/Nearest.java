package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * 最近
 * @author crtf
 */
@Data
public class Nearest{

	/**
     * 降水强度
	 */
	@JsonProperty("intensity")
	private double intensity;

	/**
     * 降水距离
	 */
	@JsonProperty("distance")
	private double distance;

	/**
     * 状态
	 */
	@JsonProperty("status")
	private String status;


}