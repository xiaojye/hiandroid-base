package com.jye.hiandroid.base.restful.provider;

import com.jye.hiandroid.log.HiLog;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author jye
 * @since 1.0
 */
public class DefaultProvider implements IHttpProvider {

    @Override
    public int connectTimeout() {
        return 10;
    }

    @Override
    public int readTimeout() {
        return 10;
    }

    @Override
    public int writeTimeout() {
        return 10;
    }

    @Override
    public Interceptor[] interceptors() {
        return null;
    }

    @Override
    public HttpLoggingInterceptor.Logger logger() {
        return new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                HiLog.i(message);
            }
        };
    }
}
