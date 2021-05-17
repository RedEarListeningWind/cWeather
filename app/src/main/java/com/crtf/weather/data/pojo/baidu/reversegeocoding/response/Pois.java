package com.crtf.weather.data.pojo.baidu.reversegeocoding.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 周边poi
 */
@Data
public class Pois{
	/**
	 * 地址信息
	 */
	@JsonProperty("addr")
	private String addr;

    /**
     * 数据来源（已废弃）
	 */
	@JsonProperty("cp")
	private String cp;

	/**
	 * 和当前坐标点的方向
	 */
	@JsonProperty("direction")
	private String direction;

	/**
	 * 离坐标点距离
	 */
	@JsonProperty("distance")
	private String distance;

	/**
	 * poi名称
	 */
	@JsonProperty("name")
	private String name;

    /**
     * poi类型，如’美食;中餐厅’。tag与poiType字段均为poi类型，建议使用tag字段，信息更详细。poi详细类别
	 */
	@JsonProperty("poiType")
	private String poiType;

	/**
	 * poi类型，如’美食;中餐厅’。tag与poiType字段均为poi类型，建议使用tag字段，信息更详细。poi详细类别
	 */
	@JsonProperty("tag")
	private String tag;

	/**
	 * poi坐标{x,y}
	 */
	@JsonProperty("point")
	private Point point;

	/**
	 * 电话
	 */
	@JsonProperty("tel")
	private String tel;

	/**
	 * poi唯一标识
	 */
	@JsonProperty("uid")
	private String uid;

	/**
     * 邮编
	 */
	@JsonProperty("zip")
	private String zip;

	/**
     * poi对应的主点poi（如，海底捞的主点为上地华联，该字段则为上地华联的poi信息。如无，该字段为空），包含子字段和pois基础召回字段相同。
	 */
	@JsonProperty("parent_poi")
	private ParentPoi parentPoi;


}