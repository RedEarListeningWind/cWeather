package com.crtf.weather.data.pojo.colorfulclouds.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RequestColorfulClouds {

        /**
         * 语言选项
         *     简体中文（zh_CN，默认）、繁体中文（zh_TW）、美式英语（en_US）、英式英语（en_GB）、日语（ja）
         */
        @JsonProperty("lang")
        private Lang lang;

        /**
         * 单位制选项
         * 公制metric，英制imperial，和科学单位制 SI
         */
        @JsonProperty("unit")
        private Unit unit;

        /**
         * 小时步长选项
         * 1~48
         */
        @JsonProperty("hourlysteps")
        private Integer hourlySteps;

        /**
         * 天步长选项
         * 1~15
         */
        @JsonProperty("dailysteps")
        private Integer dailySteps;

        /**
         * 预警信息
         * true,false
         */
        @JsonProperty("alert")
        private Boolean alert;
}
