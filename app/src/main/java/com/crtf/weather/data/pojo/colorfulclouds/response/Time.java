package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 时间
 * @author crtf
 */
@Data
public class Time{

	@JsonProperty("time")
	private String time;

}