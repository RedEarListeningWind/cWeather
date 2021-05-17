package com.crtf.weather.data.pojo.baidu.reversegeocoding.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 地址组件
 * 国外行政区划，字段仅代表层级
 */
@Data
public class AddressComponent{

	/**
	 * 国家
	 */
	@JsonProperty("country")
	private String country;

	/**
     * 国家编码
	 */
	@JsonProperty("country_code")
	private int countryCode;

	/**
     * 国家英文缩写（三位）
	 */
	@JsonProperty("country_code_iso")
	private String countryCodeIso;

	/**
     * 国家英文缩写（两位）
	 */
	@JsonProperty("country_code_iso2")
	private String countryCodeIso2;


    /**
	 * 省名
	 */
	@JsonProperty("province")
	private String province;

	/**
     * 城市名
	 */
	@JsonProperty("city")
	private String city;

	/**
	 * 城市所在级别（仅国外有参考意义。国外行政区划与中国有差异，
	 * 城市对应的层级不一定为『city』。country、province、city、district、town分别对应0-4级，若city_level=3，则district层级为该国家的city层级）
	 */
	@JsonProperty("city_level")
	private int cityLevel;

	/**
     * 区县名
	 */
	@JsonProperty("district")
	private String district;


    /**
     * 乡镇名，需设置extensions_town=true时才会返回
	 */
	@JsonProperty("town")
	private String town;

    /**
     * 乡镇id
	 */
	@JsonProperty("town_code")
	private String townCode;

    /**
     * 街道名（行政区划中的街道层级）
	 */
	@JsonProperty("street")
	private String street;

	/**
	 * 街道门牌号
	 */
	@JsonProperty("street_number")
	private String streetNumber;

	/**
	 * 行政区划代码
	 */
	@JsonProperty("adcode")
	private String adcode;

	/**
	 * 相对当前坐标点的方向，当有门牌号的时候返回数据
	 */
	@JsonProperty("direction")
	private String direction;

	/**
     * 相对当前坐标点的距离，当有门牌号的时候返回数据
	 */
	@JsonProperty("distance")
	private String distance;

}