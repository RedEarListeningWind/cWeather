package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 风
 * @author crtf
 */
@Data
public class Wind{

	/**
     * 风速
	 */
	@JsonProperty("speed")
	private double speed;
    /**
     * 风向
	 */
	@JsonProperty("direction")
	private double direction;

}