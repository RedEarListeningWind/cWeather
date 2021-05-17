package com.crtf.weather.data.pojo.baidu.geocoding.request;

import com.crtf.weather.data.pojo.baidu.general.request.CoordinateType;
import com.crtf.weather.data.pojo.baidu.general.request.Output;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 请求 地理编码
 */
@Data
public class RequestGeocoding {

    /**
     * 待解析的地址。最多支持84个字节。
     * 可以输入两种样式的值，分别是：
     * .    1、标准的结构化地址信息，如北京市海淀区上地十街十号 【推荐，地址结构越完整，解析精度越高】
     * .    2、支持“*路与*路交叉口”描述方式，如北一环路和阜阳路的交叉路口
     * 第二种方式并不总是有返回结果，只有当地址库中存在该地址描述时才有返回。
     * .
     * 举例
     * .   北京市海淀区上地十街10号
     * .
     * 默认值
     * .   无
     * .
     * 是否必须
     * .   是
     */
    @JsonProperty("address")
    private String address;

    /**
     * 地址所在的城市名。用于指定上述地址所在的城市，当多个城市都有上述地址时，该参数起到过滤作用，但不限制坐标召回城市。
     * 举例
     * .    北京市
     * 默认值
     * .    无
     * 是否必须
     * .    否
     */
    @JsonProperty("city")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String city;

    /**
     * 可选参数，添加后返回国测局经纬度坐标或百度米制坐标 坐标系说明
     * .
     * 举例
     * .    gcj02ll（国测局坐标）、bd09mc（百度墨卡托坐标）
     * 默认值
     * .    bd09ll（百度经纬度坐标）
     * 是否必须
     * .    否
     */
    @JsonProperty("ret_coordtype")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CoordinateType retCoordtype;

    /**
     * 用户申请注册的key，自v2开始参数修改为“ak”，之前版本参数为“key”申请ak
     * .
     * 默认值
     * .    无
     * 是否必须
     * .    是
     */
    @JsonProperty("ak")
    private String ak;

    /**
     * 若用户所用ak的校验方式为sn校验时该参数必须[sn生成]
     * 默认值
     * .    无
     * 是否必须
     * .    否
     */
    @JsonProperty("sn")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sn;

    /**
     * 输出格式为json或者xml
     * 举例
     * .    json或xml
     * 默认值
     * .    xml
     * 是否必须
     * .    否
     */
    @JsonProperty("output")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Output output;

    /**
     * 将json格式的返回值通过callback函数返回以实现jsonp功能
     * .
     * 举例
     * .    callback=showLocation(JavaScript函数名)
     * 默认值
     * .    无
     * 是否必须
     * .    否
     */
    @JsonProperty("callback")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String callback;


    /**
     * 安全码
     */
    @JsonProperty("mcode")
    private String mcode;
}
