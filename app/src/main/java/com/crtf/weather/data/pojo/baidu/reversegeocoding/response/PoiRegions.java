package com.crtf.weather.data.pojo.baidu.reversegeocoding.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PoiRegions {

    /**
     * 方向说明
     * 请求中的坐标与所归属区域面的相对位置关系
     */
    @JsonProperty("direction_desc")
    private String directionDesc;

    /**
     * 归属区域面名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 归属区域面类型
     */
    @JsonProperty("tag")
    private String tag;

    @JsonProperty("uid")
    private String uId;

    /**
     * 距离
     */
    @JsonProperty("distance")
    private String distance;

}
