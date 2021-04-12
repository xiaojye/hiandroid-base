package com.jye.hiandroid.base.json;

import java.lang.reflect.Type;
import java.util.List;

/**
 * json转换器接口
 *
 * @author jye
 * @since 1.0
 */
public interface IConverter {

    /**
     * 将对象转成json格式
     */
    String objectToJson(Object object);

    /**
     * 将json转成特定的cls的对象
     */
    <T> T jsonToObject(String json, Class<T> cls);

    /**
     * 将json转换成指定类型
     */
    <T> T jsonToObject(String json, Type type);

    /**
     * 将一个json字符串，转换成一个集合
     */
    <T> List<T> jsonToList(String json, Class<T> cls);

}
