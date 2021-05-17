package com.crtf.weather.data.pojo.colorfulclouds.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import lombok.Data;

/**
 * @author crtf
 * @version V1.0
 * @className IconAndBackgroundColor
 * @description 显示的天气图片和背景颜色 日出图标 日落图标
 * @date 2021/5/16 23:39
 */
@Data
public class UiProperty {

    @JsonProperty("icon")
    private int icon;
    @JsonProperty("background_color")
    private int backgroundColor;

    // iconSunrise 日出图标
    @JsonProperty("icon_sunrise")
    protected Integer iconSunrise;

    // iconSunset  日落图标
    @JsonProperty("icon_sunset")
    protected Integer iconSunset;
}

