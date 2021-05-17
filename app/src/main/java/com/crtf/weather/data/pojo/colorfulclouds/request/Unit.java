package com.crtf.weather.data.pojo.colorfulclouds.request;

/**
 * 单位
 */
public enum Unit {
    //
    METRIC("公制","metric"),
    IMPERIAL("英制","imperial"),
    SI("和科学单位制","SI");

    private final String unitName;
    private final String code;

    Unit(String unitName, String code) {
        this.unitName = unitName;
        this.code = code;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
