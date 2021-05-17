package com.crtf.weather.data.pojo.baidu.reversegeocoding.response;

import com.crtf.weather.data.pojo.baidu.general.Location;
import com.crtf.weather.data.pojo.baidu.general.response.Result;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @author crtf
 */
@Data
public class ReverseGeocodingResult implements Result {

	/**
     * 经纬度坐标
	 */
	@JsonProperty("location")
	private Location location;

	/**
     * 结构化地址信息
	 */
	@JsonProperty("formatted_address")
	private String formattedAddress;

	/**
     * 坐标所在商圈信息，如 "人民大学,中关村,苏州街"。最多返回3个。
	 */
	@JsonProperty("business")
	private String business;

	/**
	 * 地址组件
	 * 国外行政区划，字段仅代表层级
	 */
	@JsonProperty("addressComponent")
	private AddressComponent addressComponent;

    /**
     * 周边poi数组
	 */
	@JsonProperty("pois")
	private List<Pois> pois;

    /**
     * 道路
	 */
	@JsonProperty("roads")
	private List<Roads> roads;

	/**
	 * poi 地区
	 */
	@JsonProperty("poiRegions")
	private List<PoiRegions> poiRegions;


	/**
	 * 当前位置结合POI的语义化结果描述。需设置extensions_poi=1才能返回。
	 */
	@JsonProperty("sematic_description")
	private String sematicDescription;

	/**
     * 百度定义的城市id（正常更新与维护，但建议使用adcode）
	 */
	@JsonProperty("cityCode")
	private int cityCode;

}