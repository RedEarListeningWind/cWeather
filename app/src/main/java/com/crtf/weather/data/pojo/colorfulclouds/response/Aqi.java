package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * 空气质量指数
 * @author crtf
 */
@Data
public class Aqi{


	@JsonProperty("usa")
	private double usa;

	@JsonProperty("chn")
	private double chn;

}