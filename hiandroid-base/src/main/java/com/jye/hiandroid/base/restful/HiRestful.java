package com.jye.hiandroid.base.restful;

import androidx.annotation.NonNull;

import com.jye.hiandroid.base.json.gson.GsonFactory;
import com.jye.hiandroid.base.restful.provider.DefaultProvider;
import com.jye.hiandroid.base.restful.provider.IHttpProvider;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit工厂类
 *
 * @author jye
 * @since 1.0
 */
public final class HiRestful {

    private String mGlobalBaseUrl;
    private IHttpProvider mGlobalProvider;

    private HashMap<String, Retrofit> mRetrofitMap = new HashMap<>();
    private HashMap<String, IHttpProvider> mProviderMap = new HashMap<>();

    private static HiRestful sInstance = null;

    private HiRestful() {
    }

    public static HiRestful getInstance() {
        if (sInstance == null) {
            synchronized (HiRestful.class) {
                if (sInstance == null) {
                    sInstance = new HiRestful();
                }
            }
        }
        return sInstance;
    }

    /**
     * 设置全局IHttpProvider
     *
     * @param provider
     */
    public void setGlobalProvider(String baseUrl, IHttpProvider provider) {
        this.mGlobalBaseUrl = baseUrl;
        this.mGlobalProvider = provider;
        registerProvider(baseUrl, provider);
    }

    /**
     * 注册IHttpProvider
     *
     * @param baseUrl
     * @param provider
     */
    public void registerProvider(@NonNull String baseUrl, IHttpProvider provider) {
        mProviderMap.put(baseUrl, provider);
    }

    /**
     * 创建ApiService
     *
     * @param service
     * @param <S>
     * @return
     */
    public <S> S createApi(Class<S> service) {
        return getRetrofit(mGlobalBaseUrl).create(service);
    }

    /**
     * 创建ApiService
     *
     * @param baseUrl
     * @param service
     * @param <S>
     * @return
     */
    public <S> S createApi(String baseUrl, Class<S> service) {
        return getRetrofit(baseUrl).create(service);
    }

    /**
     * 获取Retrofit对象
     *
     * @param baseUrl
     * @return
     */
    public Retrofit getRetrofit(@NonNull String baseUrl) {
        return getRetrofit(baseUrl, null);
    }

    /**
     * 获取Retrofit对象
     *
     * @param baseUrl
     * @param provider
     * @return
     */
    public Retrofit getRetrofit(@NonNull String baseUrl, IHttpProvider provider) {
        //判断baseUrl是否为空，为空时提示错误
        if (baseUrl.isEmpty()) {
            throw new IllegalStateException("baseUrl 不能为空");
        }

        //根据BaseUrl查询是否已创建过Retrofit，如果存在则直接返回（避免重复创建，浪费资源）
        Retrofit retrofitInstance = mRetrofitMap.get(baseUrl);
        if (retrofitInstance != null) {
            return retrofitInstance;
        }

        if (provider == null) {
            //根据BaseUrl获取注册的IHttpProvider，如果不存在则使用DefaultProvider
            provider = mProviderMap.get(baseUrl);
            if (provider == null) {
                provider = mGlobalProvider;
                if (provider == null) {
                    provider = new DefaultProvider();
                }
            }
        }

        //构建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(buildOkHttpClient(provider))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonFactory.get()))
                .build();

        mRetrofitMap.put(baseUrl, retrofit);
        mProviderMap.put(baseUrl, provider);

        return retrofit;
    }

    /**
     * 构建OkHttpClient
     *
     * @param provider
     * @return
     */
    private OkHttpClient buildOkHttpClient(IHttpProvider provider) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                //配置读取超时时间
                .readTimeout(provider.readTimeout(), TimeUnit.SECONDS)
                //配置写入超时时间
                .writeTimeout(provider.writeTimeout(), TimeUnit.SECONDS)
                //配置连接超时时间
                .connectTimeout(provider.connectTimeout(), TimeUnit.SECONDS);

        //配置拦截器
        Interceptor[] interceptors = provider.interceptors();
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                httpBuilder.addInterceptor(interceptor);
            }
        }

        //添加日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(provider.logger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpBuilder.addInterceptor(loggingInterceptor);

        //构建OkHttpClient并返回
        return httpBuilder.build();
    }

    public HashMap<String, Retrofit> getRetrofitMap() {
        return mRetrofitMap;
    }

    public HashMap<String, IHttpProvider> getProviderMap() {
        return mProviderMap;
    }

    public void clearCache() {
        mRetrofitMap.clear();
        mProviderMap.clear();
    }
}