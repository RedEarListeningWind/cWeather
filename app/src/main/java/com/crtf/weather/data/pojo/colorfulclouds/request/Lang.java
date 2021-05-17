package com.crtf.weather.data.pojo.colorfulclouds.request;

/**
 * 语言
 */
public enum Lang {

    //
    ZH_CN("简体中文","zh_CN"),
    ZH_TW("繁体中文","zh_TW"),
    EN_US("美式英语","en_US"),
    EN_GB("英式英语","en_GB"),
    JA("日语","ja");

    private final String langName;
    private final String code;

    Lang(String langName, String code) {
        this.langName = langName;
        this.code = code;
    }

    public String getLangName() {
        return langName;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
