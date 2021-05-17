package com.crtf.weather.data.pojo.baidu.reversegeocoding.request;

import com.crtf.weather.data.pojo.baidu.general.Location;
import com.crtf.weather.data.pojo.baidu.general.request.CoordinateType;
import com.crtf.weather.data.pojo.baidu.general.request.Output;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
/**
 * 请求 反向地理编码
 * @author crtf
 */
@Data
public class RequestReverseGeocoding {

    /**
     * location
     * 根据经纬度坐标获取地址。
     * float
     * 38.76623,116.43213
     * lat<纬度>,lng<经度>
     * 无
     * 是
     */
    @JsonProperty("location")
    private Location location;


    /**
     * coordtype
     * 坐标的类型，目前支持的坐标类型包括：bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标，仅限中国）、wgs84ll（ GPS经纬度） 坐标系说明
     * string
     * bd09ll、gcj02ll	bd09ll
     * 否
     */
    @JsonProperty("coordtype")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CoordinateType coordinateType;

    /**
     * ret_coordtype
     * 可选参数，添加后返回国测局经纬度坐标或百度米制坐标 坐标系说明
     * string	gcj02ll（国测局坐标，仅限中国）、bd09mc（百度墨卡托坐标）
     * bd09ll（百度经纬度坐标）
     * 否
     */
    @JsonProperty("ret_coordtype")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CoordinateType retCoordinateType;

    /**
     * radius
     * poi召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。
     * int
     * 500
     * 1000
     * 否
     */
    @JsonProperty("radius")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer radius;

    /**
     * ak
     * 用户申请注册的key，自v2开始参数修改为“ak”，之前版本参数为“key” 申请ak	string
     * E4805d16520de693a3fe70
     * 无
     * 是
     */
    @JsonProperty("ak")
    private String ak;

    /**
     * sn
     * 若用户所用ak的校验方式为sn校验时该参数必须
     * sn生成
     * string
     * 无
     * 否
     */
    @JsonProperty("sn")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sn;


    /**
     * output
     * 输出格式为json或者xml
     * string
     * json或xml
     * xml
     * 否
     */
    @JsonProperty("output")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Output output;

    /**
     * callback
     * 将json格式的返回值通过callback函数返回以实现jsonp功能
     * string
     * callback=showLocation(JavaScript函数名)
     * 无
     * 否
     */
    @JsonProperty("callback")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String callback;

    /**
     * poi_types
     * 可以选择poi类型召回不同类型的poi，例如poi_types=酒店，如想召回多个POI类型数据，可以‘|’分割
     * 例如poi_types=酒店|房地产
     * 不添加该参数则默认召回全部POI分类数据。poi分类
     * string
     * poi_types=酒店
     * poi_types=酒店|房地产
     * 无
     * 否
     */
    @JsonProperty("poi_types")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String poiTypes;

    /**
     * extensions_poi
     * extensions_poi=0，不召回pois数据。
     * extensions_poi=1，返回pois数据（默认显示周边1000米内的poi），并返回sematic_description语义化数据。
     * <p>
     * string
     * 0
     * 无
     * 否
     */
    @JsonProperty("extensions_poi")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer extensionsPoi;

    /**
     * extensions_road
     * 当取值为true时，召回坐标周围最近的3条道路数据。区别于行政区划中的street参数（street参数为行政区划中的街道，和普通道路不对应）。
     * string
     * false、true
     * false
     * 否
     */
    @JsonProperty("extensions_road")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean extensionsRoad;

    /**
     * extensions_town
     * 当取值为true时，行政区划返回乡镇级数据（town），仅国内召回乡镇数据。默认不访问。
     * string
     * true
     * 无
     * 否
     */
    @JsonProperty("extensions_town")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean extensionsTown;

    /**
     * language
     * 指定召回的行政区划语言类型。
     * 召回行政区划语言list（全量支持的语言见示例）。
     * 当language=local时，根据请求中坐标所对应国家的母语类型，自动选择对应语言类型的行政区划召回。
     * 目前支持多语言的行政区划区划包含country、province、city、district
     * string
     * el gu en vi ca it iw sv eu ar cs gl id es en-GB ru sr nl pt tr tl lv en-AU lt th ro fil ta fr bg hr bn de hu fa hi pt-BR fi da ja te pt-PT ml ko kn sk zh-CN pl uk sl mr
     * local
     * en，国内默认zh-CN
     * 否
     */
    @JsonProperty("language")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String language;

    /**
     * language_auto
     * 当用户指定language参数时，是否自动填充行政区划。
     * 1填充，0不填充。
     * 填充：当服务按某种语言类别召回时，若某一行政区划层级的语言数据未覆盖，则按照“英文→中文→本地语言”类别行政区划数据对该层级行政区划进行填充，保证行政区划数据召回完整性。
     * int
     * 0、1
     * 无
     * 否
     */
    @JsonProperty("language_auto")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer languageAuto;

    /**
     * 安全码
     */
    @JsonProperty("mcode")
    private String mcode;

}