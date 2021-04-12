package com.jye.hiandroid.base.restful.provider;

import androidx.annotation.Nullable;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 网络提供者接口
 *
 * @author jye
 * @since 1.0
 */
public interface IHttpProvider {

    /**
     * @return 配置连接超时时间（秒）
     */
    int connectTimeout();

    /**
     * @return 配置读取超时时间（秒）
     */
    int readTimeout();

    /**
     * @return 配置写入超时时间（秒）
     */
    int writeTimeout();

    /**
     * @return 配置拦截器
     */
    @Nullable
    Interceptor[] interceptors();

    /**
     * @return 配置Logger
     */
    HttpLoggingInterceptor.Logger logger();

}