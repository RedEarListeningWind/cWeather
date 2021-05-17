package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * 降水
 * @author crtf
 */
@Data
public class Precipitation{

	/**
     * 最近
	 */
	@JsonProperty("nearest")
	private Nearest nearest;

	/**
     * 本地
	 */
	@JsonProperty("local")
	private Local local;


}