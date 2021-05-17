package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 日出日落
 * @author crtf
 */
@Getter
@Setter
public class Astro{

	/**
	 * 日出
	 */
	@JsonProperty("sunrise")
	private Time sunrise;

	/**
	 * 日落
	 */
	@JsonProperty("sunset")
	private Time sunset;


}