package com.jye.hiandroid.base.json.gson;

import com.google.gson.Gson;
import com.jye.hiandroid.base.json.IConverter;
import com.jye.hiandroid.util.HiParameterizedTypeImpl;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Gson实现的Json转换器
 *
 * @author jye
 * @since 1.0
 */
public class GsonConverter implements IConverter {

    private final Gson gson = GsonFactory.get();

    public String objectToJson(Object object) {
        return gson.toJson(object);
    }

    public <T> T jsonToObject(String json, Class<T> cls) {
        return gson.fromJson(json, cls);
    }

    public <T> T jsonToObject(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public <T> List<T> jsonToList(String json, Class<T> cls) {
        Type type = new HiParameterizedTypeImpl(cls);
        return gson.fromJson(json, type);
    }

}
