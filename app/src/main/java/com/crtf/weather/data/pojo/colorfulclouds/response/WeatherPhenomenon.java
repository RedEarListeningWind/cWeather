package com.crtf.weather.data.pojo.colorfulclouds.response;


import com.crtf.weather.R;
import com.crtf.weather.data.pojo.colorfulclouds.ui.UiProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * 天气现象
 * 主要天气现象的优先级：降雪 > 降雨 > 雾 > 沙尘 > 浮尘 > 雾霾 > 大风 > 阴 > 多云 > 晴
 */
public enum WeatherPhenomenon {


    // 代码       天气现象                                备注
    CLEAR_DAY("晴", "白天", "cloudrate < 0.2",R.color.clear_day, R.drawable.ic_tq_10_qing_zhou, R.drawable.ic_tq_11_ri_chu, R.drawable.ic_tq_12_ri_la) {
        @Override
        public boolean isSunnyDay() {
            return true;
        }

        @Override
        public int getIconSunrise(String iconSunriseName) {
            return this.uiProperty.get(iconSunriseName == null ? getDefaultKey() : iconSunriseName).getIconSunrise();
        }

        @Override
        public int getIconSunset(String iconSunsetName) {
            return this.uiProperty.get(iconSunsetName == null ? getDefaultKey() : iconSunsetName).getIconSunset();
        }
    },
    CLEAR_NIGHT("晴", "夜间", "cloudrate < 0.2",R.color.clear_night, R.drawable.ic_tq_09_qing_ye),
    PARTLY_CLOUDY_DAY("多云", "白天", "0.8 >= cloudrate > 0.2",R.color.partly_cloudy_day, R.drawable.ic_tq_07_duo_yun_zhou),
    PARTLY_CLOUDY_NIGHT("多云", "夜间", "0.8 >= cloudrate > 0.2",R.color.partly_cloudy_night, R.drawable.ic_tq_06_duo_yun_ye),
    CLOUDY("阴", "cloudrate > 0.8",R.color.cloudy, R.drawable.ic_tq_05_yin),
    LIGHT_HAZE("轻度雾霾", "PM2.5 100~150",R.color.light_haze, R.drawable.ic_tq_20_qing_du_wu_mai),
    MODERATE_HAZE("中度雾霾", "PM2.5 150~200",R.color.moderate_haze, R.drawable.ic_tq_21_zhong_du_wu_mai_1),
    HEAVY_HAZE("重度雾霾", "PM2.5 > 200",R.color.heavy_haze, R.drawable.ic_tq_22_zhong_du_wu_mai_2),
    LIGHT_RAIN("小雨",R.color.light_rain, R.drawable.ic_tq_15_xiao_yu),
    MODERATE_RAIN("中雨",R.color.moderate_rain, R.drawable.ic_tq_18_zhong_yu),
    HEAVY_RAIN("大雨",R.color.heavy_rain, R.drawable.ic_tq_03_da_yu),
    STORM_RAIN("暴雨",R.color.storm_rain, R.drawable.ic_tq_00_da_bao_yu),
    FOG("雾", "能见度低，湿度高，风速低，温度低",R.color.fog, R.drawable.ic_tq_19_wu),
    LIGHT_SNOW("小雪",R.color.light_snow, R.drawable.ic_tq_14_xiao_xue),
    MODERATE_SNOW("中雪",R.color.moderate_snow, R.drawable.ic_tq_17_zhong_xue),
    HEAVY_SNOW("大雪",R.color.heavy_snow, R.drawable.ic_tq_02_da_xue),
    STORM_SNOW("暴雪",R.color.storm_snow, R.drawable.ic_tq_13_te_da_bao_xue),
    DUST("浮尘", "aqi > 150，pm10 > 150，湿度 < 30%，风速 < 6 m/s",R.color.dust, R.drawable.ic_tq_08_fu_chen),
    SAND("沙尘", "aqi > 150，pm10 > 150，湿度 < 30%，风速 > 6 m/s",R.color.sand, R.drawable.ic_tq_16_yang_sha),
    WIND("大风",R.color.wind, R.drawable.ic_tq_01_da_feng);


    /**
     * 现象
     */
    private final String phenomenon;
    /**
     * 别名
     */
    private final String alias;
    /**
     * 备注
     */
    private final String comment;

    protected final Map<String, UiProperty> uiProperty;



    private static final String DEFAULT_KEY = "default";

    WeatherPhenomenon(String phenomenon,int backgroundColor, int iconId) {
        this(phenomenon, null,backgroundColor, iconId);
    }

    WeatherPhenomenon(String phenomenon, String comment,int backgroundColor, int iconId) {
        this(phenomenon, null, comment,backgroundColor, iconId, null, null);
    }

    WeatherPhenomenon(String phenomenon, String alias, String comment,int backgroundColor, int icon) {
        this(phenomenon, alias, comment,backgroundColor, icon, null, null);
    }

    /**
     * @param phenomenon  现象
     * @param alias       别名
     * @param comment     描述
     * @param iconId      图标
     * @param iconSunrise 日出图标
     * @param iconSunset  日落图标
     */
    WeatherPhenomenon(String phenomenon, String alias, String comment,int backgroundColor, int iconId, Integer iconSunrise, Integer iconSunset) {
        this.phenomenon = phenomenon;
        this.alias = alias;
        this.comment = comment;
        this.uiProperty = new HashMap<>();
        final UiProperty uiProperty = new UiProperty();
        uiProperty.setIcon(iconId);
        uiProperty.setBackgroundColor(backgroundColor);
        this.uiProperty.put(getDefaultKey(), uiProperty);
        if (this.phenomenon.equals("晴") && this.alias.equals("白天")) {
            this.uiProperty.get(getDefaultKey()).setIconSunrise(iconSunrise);
            this.uiProperty.get(getDefaultKey()).setIconSunset( iconSunset);
        }
    }

    /**
     * @return 是否晴(白天)
     */
    public boolean isSunnyDay() {
        return false;
    }

    public static String getDefaultKey() {
        return DEFAULT_KEY;
    }

    /**
     * @param iconName 为 null 时，返回默认
     * @return
     */
    public int getIcon(String iconName) {
        return this.uiProperty.get(iconName == null ? getDefaultKey() : iconName).getIcon();
    }

    public int getBackgroundColor(String color){
        return this.uiProperty.get(color == null?getDefaultKey():color).getBackgroundColor();
    }

    public int getIconSunrise(String iconSunriseName) {
        throw new UnsupportedOperationException("不支持非 晴(白天) 调用!");
    }

    public void putIconSunrise(String iconSunriseName, int icon) {
        throw new UnsupportedOperationException("不支持非 晴(白天) 调用!");
    }

    public int getIconSunset(String iconSunsetName) {
        throw new UnsupportedOperationException("不支持非 晴(白天) 调用!");
    }

    public void setIconSunset(String iconSunset, int icon) {
        throw new UnsupportedOperationException("不支持非 晴(白天) 调用!");
    }

    /**
     * 现象
     */
    public String getPhenomenon() {
        return phenomenon;
    }

    /**
     * 别名
     */
    public String getAlias() {
        return alias;
    }

    /**
     * 备注
     */
    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "WeatherPhenomenon{" +
                "phenomenon='" + phenomenon + '\'' +
                ", alias='" + alias + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
