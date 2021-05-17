package com.crtf.weather.util.okhttp;

import com.crtf.weather.data.pojo.baidu.general.Location;
import com.crtf.weather.data.pojo.baidu.general.request.CoordinateType;
import com.crtf.weather.data.pojo.baidu.general.request.Output;
import com.crtf.weather.data.pojo.baidu.general.response.BdResponse;
import com.crtf.weather.data.pojo.baidu.reversegeocoding.request.RequestReverseGeocoding;
import com.crtf.weather.data.pojo.baidu.reversegeocoding.response.ReverseGeocodingResult;
import com.crtf.weather.util.jackson.JacksonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import okhttp3.FormBody;
import okhttp3.RequestBody;

import static org.junit.Assert.*;

public class OkHttpUtilTest {

    @Test
    public void get() throws IOException {
        System.out.println(OkHttpUtil.get(new URL("https://www.baidu.com")));
    }

    @Test
    public void post() throws IOException {
        final RequestReverseGeocoding reverseGeocoding = new RequestReverseGeocoding();
        final Location location = new Location();
        location.setLat(33.04209886);
        location.setLng(112.58773506);
        reverseGeocoding.setLocation(location);
        reverseGeocoding.setCoordinateType(CoordinateType.WGS84LL);
        reverseGeocoding.setRetCoordinateType(CoordinateType.GCJ02LL);
        reverseGeocoding.setRadius(500);
        reverseGeocoding.setAk("Drcd7ZS8GbDkY5cAUKxOllVGGGOn5kqo");
        reverseGeocoding.setOutput(Output.JSON);
        reverseGeocoding.setExtensionsPoi(1);
        reverseGeocoding.setExtensionsRoad(true);
        reverseGeocoding.setExtensionsTown(true);
        reverseGeocoding.setLanguage("local");
        reverseGeocoding.setLanguageAuto(1);
        reverseGeocoding.setMcode("07:D2:C9:C0:E9:56:79:8E:53:89:D8:BD:03:4B:0F:14:1E:00:3E:5E;com.crtf.weather");

        final FormBody.Builder builder = new FormBody.Builder();

        extractAllValuesAssignToForm(reverseGeocoding,builder);

        final String post = OkHttpUtil.post(new URL("http://api.map.baidu.com/reverse_geocoding/v3/"), builder.build());
        final BdResponse<ReverseGeocodingResult> bdResponse = JacksonUtil.deserializerFromString(new TypeReference<BdResponse<ReverseGeocodingResult>>() {
        }, post);
        System.out.println(bdResponse);
    }


    /**
     * 提取所有值并将其分配给表单
     */
    private void extractAllValuesAssignToForm(Object object, FormBody.Builder fromBody) {
        final Class<?> aClass = object.getClass();
        final Field[] fields = aClass.getDeclaredFields();
        Arrays.stream(fields).forEach(f -> {
            f.setAccessible(true);
            try {
                Object o = f.get(object);

                if (o != null) {
                    final JsonProperty jsonProperty = f.getAnnotation(JsonProperty.class);
                    if (jsonProperty != null) {
                        final String value = jsonProperty.value();
                        if (!JsonProperty.USE_DEFAULT_NAME.equals(value)) {
                            fromBody.add(value, o.toString());
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

}