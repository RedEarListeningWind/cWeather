package com.crtf.weather.data.pojo.ui.adapter;

import com.crtf.weather.data.pojo.baidu.general.response.BdResponse;
import com.crtf.weather.data.pojo.baidu.reversegeocoding.response.ReverseGeocodingResult;
import com.crtf.weather.data.pojo.colorfulclouds.response.ResponseColorfulClouds;

import lombok.Data;

/**
 * @author crtf
 * @version V1.0
 * @className ReverseGeocodingAndColorfulClouds
 * @description 位置切换适配器 {@link com.crtf.weather.ui.main.location.SwitchLocationAdapter} 中的天气数据和反向地理位置数据
 * @date 2021/5/16 12:09
 */
@Data
public class ReverseGeocodingAndColorfulClouds {

    private ResponseColorfulClouds responseColorfulClouds;
    private BdResponse<ReverseGeocodingResult> reverseGeocodingResultBdResponse;


}

