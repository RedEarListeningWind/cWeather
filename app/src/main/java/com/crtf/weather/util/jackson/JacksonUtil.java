package com.crtf.weather.util.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author crtf
 * @date 2021年5月15日 星期六 0:41
 * @version 1.0
 */
public class JacksonUtil {

    private static final JsonFactory jsonFactory;

    static {
        ServiceLoader<JsonFactory> loader = ServiceLoader.load(JsonFactory.class);

        Iterator<JsonFactory> iterator = loader.iterator();
        if (iterator.hasNext()){
            jsonFactory = iterator.next();
        }else {
            jsonFactory = null;
        }
    }

    public static String serializerToString(Object pojo) throws JsonProcessingException {
        if (jsonFactory == null)
            return null;
        ObjectMapper mapper = new ObjectMapper(jsonFactory);
        // 美化输出
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(pojo);
    }

    public static <T> T deserializerFromString(TypeReference<T> typeReference,String json) throws JsonProcessingException {
        if (jsonFactory == null)
            return null;
        ObjectMapper mapper = new ObjectMapper(jsonFactory);
        return mapper.readValue(json, typeReference);
    }

}
