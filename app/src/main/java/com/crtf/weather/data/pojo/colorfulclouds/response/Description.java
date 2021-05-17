package com.crtf.weather.data.pojo.colorfulclouds.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * 实时预报自然语言描述
 * @author crtf
 */
@Data
public class Description{

	@JsonProperty("usa")
	private String usa;

	@JsonProperty("chn")
	private String chn;
}