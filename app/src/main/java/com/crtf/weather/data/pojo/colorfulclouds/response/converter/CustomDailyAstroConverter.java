package com.crtf.weather.data.pojo.colorfulclouds.response.converter;

import com.crtf.weather.data.pojo.colorfulclouds.response.Astro;
import com.crtf.weather.data.pojo.colorfulclouds.response.Datetime;
import com.crtf.weather.data.pojo.colorfulclouds.response.DatetimeAndValue;
import com.crtf.weather.data.pojo.colorfulclouds.response.Time;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomDailyAstroConverter extends StdConverter<List<Map<String,Object>>,List<DatetimeAndValue<Astro>>> {
    @Override
    public List<DatetimeAndValue<Astro>> convert(List<Map<String, Object>> value) {
        return value.parallelStream().map(map -> {
            DatetimeAndValue<Astro> newDatetimeAndAstro = new DatetimeAndValue<>();
            Datetime datetime = new Datetime((String) map.get("date"));
            newDatetimeAndAstro.setDatetime(datetime);

            Astro newAstro = new Astro();

            Time sunrise = new Time();
            Time sunset = new Time();
            sunrise.setTime(((Map<String,String>)map.get("sunrise")).get("time"));
            sunset.setTime(((Map<String,String>)map.get("sunset")).get("time"));
            newAstro.setSunrise(sunrise);
            newAstro.setSunset(sunset);

            newDatetimeAndAstro.setValue(newAstro);
            return newDatetimeAndAstro;
        }).collect(Collectors.toList());
    }
}
