package com.crtf.weather.data.pojo.baidu.reversegeocoding.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Roads {

    /**
     * 周边道路名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 传入的坐标点距离道路的大概距离
     */
    @JsonProperty("distance")
    private String distance;
}
