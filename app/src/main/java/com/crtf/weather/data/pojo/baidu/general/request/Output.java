package com.crtf.weather.data.pojo.baidu.general.request;

/**
 * 输出格式为json或者xml
 * @author crtf
 */
public enum Output {

    // json
    JSON("json"),
    // xml
    XML("xml");

    private final String lowercase;

    Output(String lowercase) {
        this.lowercase = lowercase;
    }

    public String getLowercase() {
        return lowercase;
    }

    @Override
    public String toString() {
        return this.lowercase;
    }
}
