package com.crtf.weather.data.pojo.baidu.reversegeocoding.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Point{

	/**
     * x
	 */
	@JsonProperty("x")
	private double X;

	/**
     * y
	 */
	@JsonProperty("y")
	private double Y;
}