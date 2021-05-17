package com.crtf.weather.service.main.network.operation;

import com.crtf.weather.data.pojo.baidu.general.Location;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleNetworkOperationTest {
    private final SimpleNetworkOperation simpleNetworkOperation = new SimpleNetworkOperation();
    private final Location location = new Location();

    public SimpleNetworkOperationTest() {
        location.setLat(33.04361609441857);
        location.setLng(112.60357400044758);
    }

    @Test
    public void acquisitionWeatherData() {
        simpleNetworkOperation.acquisitionWeatherData(location,0);
    }

    @Test
    public void geocoding() {
        System.out.println(simpleNetworkOperation.geocoding("河南省南阳市杜诗路河南工业职业技术学院(新校区)"));
    }

    @Test
    public void reverseGeocoding() {
        simpleNetworkOperation.reverseGeocoding(location,0);
    }
}