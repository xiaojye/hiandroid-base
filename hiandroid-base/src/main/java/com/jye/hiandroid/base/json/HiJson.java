package com.jye.hiandroid.base.json;

import com.jye.hiandroid.base.json.gson.GsonConverter;

import java.lang.reflect.Type;
import java.util.List;

/**
 * json组件
 *
 * @author jye
 * @since 1.0
 */
public class HiJson {

    //默认使用JSON转换器
    private static IConverter sConverter = new GsonConverter();

    /**
     * 设置JSON转换器实例
     *
     * @param converter JSON转换器实例
     */
    public static void setConvert(IConverter converter) {
        sConverter = converter;
    }

    /**
     * 用于将一个对象转换为JSON字符串
     *
     * @param object 对象实例
     * @return JSON字符串
     */
    public static String toJsonString(Object object) {
        return sConverter.objectToJson(object);
    }

    /**
     * 用于将一个JSON字符串转换为对象
     *
     * @param text  JSON字符串
     * @param clazz 对象类名
     * @return 转换的对象实例
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        return sConverter.jsonToObject(text, clazz);
    }

    /**
     * 用于将一个JSON字符串转换成指定类型
     *
     * @param text JSON字符串
     * @param type 复杂类型
     * @return 转换的对象实例
     */
    public static <T> T parseObject(String text, Type type) {
        return sConverter.jsonToObject(text, type);
    }

    /**
     * 用于将一个JSON字符串转换为List
     *
     * @param text  JSON字符串
     * @param clazz List的泛型对象类名
     * @return 转换的List实例
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return sConverter.jsonToList(text, clazz);
    }
}
