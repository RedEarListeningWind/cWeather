package com.crtf.weather.data.pojo.baidu.general.request;

/**
 * 坐标体系
 */
public enum CoordinateType {
    // 为百度坐标系，在GCJ02坐标系基础上再次加密。其中bd09ll表示百度经纬度坐标，
    BD09LL("bd09ll", "百度经纬度坐标"),
    // 表示百度墨卡托米制坐标。
    BD09MC("bd09mc", "百度米制坐标"),
    // 又称火星坐标系，是由中国国家测绘局制定的地理坐标系统，是由WGS84加密后得到的坐标系。
    GCJ02LL("gcj02ll", "国测局经纬度坐标，仅限中国"),
    // 为一种大地坐标系，也是目前广泛使用的GPS全球卫星定位系统使用的坐标系。
    WGS84LL("wgs84ll", "GPS经纬度");

    private final String shorthand;
    private final String deac;


    CoordinateType(String shorthand, String deac) {
        this.shorthand = shorthand;
        this.deac = deac;
    }


    public String getShorthand() {
        return this.shorthand;
    }

    public String getDeac(){
        return this.deac;
    }

    @Override
    public String toString() {
        return shorthand;
    }
}
