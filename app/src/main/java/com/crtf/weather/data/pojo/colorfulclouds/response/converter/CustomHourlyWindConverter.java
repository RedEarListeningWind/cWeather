package com.crtf.weather.data.pojo.colorfulclouds.response.converter;

import com.crtf.weather.data.pojo.colorfulclouds.response.Datetime;
import com.crtf.weather.data.pojo.colorfulclouds.response.DatetimeAndValue;
import com.crtf.weather.data.pojo.colorfulclouds.response.Wind;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定制 小时 风 转换器
 * @author crtf
 * @version 1.0
 * @date 2021年5月9日 星期日 0:37
 */
public class CustomHourlyWindConverter extends StdConverter<List<Map<String,String>>,List<DatetimeAndValue<Wind>>> {

    @Override
    public List<DatetimeAndValue<Wind>> convert(List<Map<String, String>> value) {
        return value.parallelStream().map(map -> {
            DatetimeAndValue<Wind> cloneDatetimeAndWind = new DatetimeAndValue<>();
            Datetime datetime = new Datetime(map.get("datetime"));
            cloneDatetimeAndWind.setDatetime(datetime);

            Wind cloneWind = new Wind();
            cloneWind.setSpeed(Double.parseDouble(map.get("speed")));
            cloneWind.setDirection(Double.parseDouble(map.get("direction")));

            cloneDatetimeAndWind.setValue(cloneWind);
            return cloneDatetimeAndWind;
        }).collect(Collectors.toList());
    }

}
