package com.crtf.weather.data.pojo.baidu.general;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 地点
 * 经纬度坐标
 * @author crtf
 */
@Getter
@Setter
public class Location {

	/**
	 * 经度值
	 */
	@JsonProperty("lng")
	private double lng;

	/**
	 * 纬度值
	 */
	@JsonProperty("lat")
	private double lat;

	@Override
	public String toString() {
		return lat + "," + lng;
	}
}