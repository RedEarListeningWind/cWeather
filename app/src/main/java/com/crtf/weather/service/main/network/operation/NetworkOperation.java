package com.crtf.weather.service.main.network.operation;


import com.crtf.weather.data.pojo.baidu.general.Location;
import com.crtf.weather.data.pojo.baidu.general.response.BdResponse;
import com.crtf.weather.data.pojo.baidu.geocoding.response.GeocodingResult;
import com.crtf.weather.data.pojo.baidu.reversegeocoding.response.ReverseGeocodingResult;
import com.crtf.weather.data.pojo.colorfulclouds.response.ResponseColorfulClouds;
import com.crtf.weather.service.main.Operation;

import java.util.Observer;

/**
 * 网络捆缚者
 * @see ResponseColorfulClouds 天气数据 pojo
 * @see BdResponse 百度响度
 * @see GeocodingResult 地理编码 pojo
 * @see ReverseGeocodingResult 反向地理编码 pojo
 * @author crtf
 * @date 2021年5月13日 星期四 18:35
 * @version 1.0
 */
public interface NetworkOperation extends Operation {



    /**
     * 获取天气数据e
     * @return
     */
    void acquisitionWeatherData(Location location,int position);

    /**
     * 地理编码
     * @return
     */
    BdResponse<GeocodingResult> geocoding(String addr);

    /**
     * 反向地理编码
     * @return
     */
    void reverseGeocoding(Location location,int position);


}
