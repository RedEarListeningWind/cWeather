package com.crtf.weather.data.pojo.baidu.general.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 响应 地理编码
 * @author crtf
 */
@Data
public class BdResponse<T extends Result> {

	/**
	 * 结果
	 */
	@JsonProperty("result")
	private T result;

	/**
	 * 状态
	 */
	@JsonProperty("status")
	private int status;
}