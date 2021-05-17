package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * 警报
 * @author crtf
 */
@Data
public class Alert{
	/**
	 * 地位
	 */
	@JsonProperty("status")
	private String status;

	/**
     * 内容
	 * TODO: {@link Object} 显然不适合这里
	 */
	@JsonProperty("content")
	private List<Object> content;

}