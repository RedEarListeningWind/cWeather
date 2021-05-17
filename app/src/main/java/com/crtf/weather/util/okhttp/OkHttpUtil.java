package com.crtf.weather.util.okhttp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * @author crtf
 * @date 2021年5月15日 星期六 0:41
 * @version 1.0
 */
public class OkHttpUtil {

    private static final OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient();
    }


    public static String get(URL url) throws IOException {
        return sendRequest(url, null);
    }

    /**
     * 发送请求
     * @param url
     * @param builderConsumer
     * @return
     * @throws IOException
     */
    private static String sendRequest(URL url, Consumer<Request.Builder> builderConsumer) throws IOException {
        if (url != null) {
            final Request.Builder builder = new Request.Builder().url(url);
            if (builderConsumer != null) {
                builderConsumer.accept(builder);
            }
            final Request request = builder.build();
            try (Response response = okHttpClient.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    return response.body().string();
                }
            }
        }
        return null;
    }

    public static String post(URL url, RequestBody fromBody) throws IOException {
        return sendRequest(url, builder -> builder.post(fromBody));
    }


    /**
     * 提取所有值并将其分配给表单
     */
    public static void extractAllValuesAssignToForm(Object object, BiConsumer<String,Object> biConsumer) {
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
                        if (!JsonProperty.USE_DEFAULT_NAME.equals(value) && biConsumer != null) {
                            biConsumer.accept(value,o);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }


}
