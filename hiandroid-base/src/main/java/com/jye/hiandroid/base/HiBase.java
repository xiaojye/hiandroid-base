package com.jye.hiandroid.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.jye.hiandroid.ioc.HiIoc;
import com.jye.hiandroid.ioc.annotation.IocActivity;
import com.jye.hiandroid.ioc.context.IocContext;
import com.jye.hiandroid.log.HiLogManager;
import com.jye.hiandroid.log.printer.HiConsolePrinter;
import com.jye.hiandroid.util.HiActivityManager;
import com.jye.hiandroid.util.HiToastUtils;
import com.jye.hiandroid.util.HiTypefaceUtils;

/**
 * @author jye
 * @since 1.0
 */
public class HiBase {

    private static HiBase sInstance;

    private Handler mMainHandler;
    private Application mApplication;
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks;

    /**
     * 初始化 HiBase
     *
     * @param application Application对象
     * @return HiBase
     */
    public static HiBase init(@NonNull Application application) {
        return init(application, new HiBaseConfig() {
        });
    }

    /**
     * 初始化 HiBase
     *
     * @param application Application对象
     * @param config      初始化配置
     * @return HiBase
     */
    public static HiBase init(@NonNull Application application, @NonNull final HiBaseConfig config) {
        sInstance = new HiBase(application, config);
        return sInstance;
    }

    /**
     * 获取 HiBase
     *
     * @return HiBase
     */
    public static HiBase getInstance() {
        return sInstance;
    }

    private HiBase(@NonNull Application application, @NonNull final HiBaseConfig config) {
        this.mApplication = application;
        this.mMainHandler = new Handler(Looper.getMainLooper());

        //初始化日志组件
        HiLogManager.init(config.logConfig(), new HiConsolePrinter());

        //初始化IOC组件
        final IocContext iocContext = HiIoc.init(application);

        //注册Activity生成周期监听
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                HiActivityManager.getInstance().pushActivity(activity);
                if (mActivityLifecycleCallbacks != null) {
                    mActivityLifecycleCallbacks.onActivityCreated(activity, savedInstanceState);
                }

                if (config.openIoc()) {
                    Class clazz = activity.getClass();
                    if (clazz.isAnnotationPresent(IocActivity.class)) {
                        IocActivity iocActivity = (IocActivity) clazz.getAnnotation(IocActivity.class);
                        iocContext.registerBean(iocActivity.value(), activity);

                        int layoutId = iocActivity.layout();
                        if (layoutId != 0) {
                            activity.setContentView(layoutId);
                        }

                        Object src = iocContext.getBean(clazz);
                        iocContext.setBeanData(clazz, src);
                    }
                }
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                if (mActivityLifecycleCallbacks != null) {
                    mActivityLifecycleCallbacks.onActivityStarted(activity);
                }
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                if (mActivityLifecycleCallbacks != null) {
                    mActivityLifecycleCallbacks.onActivityResumed(activity);
                }
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                if (mActivityLifecycleCallbacks != null) {
                    mActivityLifecycleCallbacks.onActivityPaused(activity);
                }
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                if (mActivityLifecycleCallbacks != null) {
                    mActivityLifecycleCallbacks.onActivityStopped(activity);
                }
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                if (mActivityLifecycleCallbacks != null) {
                    mActivityLifecycleCallbacks.onActivitySaveInstanceState(activity, outState);
                }
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                HiActivityManager.getInstance().popActivity(activity);
                if (mActivityLifecycleCallbacks != null) {
                    mActivityLifecycleCallbacks.onActivityDestroyed(activity);
                }

                if (config.openIoc()) {
                    iocContext.removeBean(iocContext.getBeanName(activity));
                }
            }
        });
    }

    /**
     * 获取全局 Application
     *
     * @return Application
     */
    public Application getApp() {
        return mApplication;
    }

    /**
     * 获取全局 Context
     *
     * @return ApplicationContext
     */
    public Context getAppContext() {
        return getApp().getApplicationContext();
    }

    /**
     * 获取全局资源对象
     *
     * @return Resources
     */
    public Resources getResources() {
        return getApp().getResources();
    }

    /**
     * 获取主线程Handler
     *
     * @return Handler
     */
    public Handler getMainHandler() {
        return mMainHandler;
    }

    /**
     * 在主线程中执行异步操作
     *
     * @param runnable 异步操作线程
     */
    public void runOnUiThread(Runnable runnable) {
        getMainHandler().post(runnable);
    }

    /**
     * 退出APP
     */
    public static void exitApp() {
        HiActivityManager.getInstance().finishAllActivity();
        System.exit(0);
    }

    /**
     * 设置应用全局默认字体
     *
     * @param fontPath 需要修改style样式为monospace：
     *                 <style name="AppTheme">
     *                 <!-- Customize your theme here. -->
     *                 <!-- Set system default typeface -->
     *                 <item name="android:typeface">monospace</item>
     *                 </style>
     * @return MCFramework
     */
    public HiBase setAppDefaultFont(String fontPath) {
        HiTypefaceUtils.replaceSystemDefaultFont(getAppContext(), fontPath);
        return this;
    }

    /**
     * 设置Toast默认的显示位置
     *
     * @param gravity 默认显示位置
     * @param xOffset x轴偏移量
     * @param yOffset y轴偏移量
     * @return MCFramework
     */
    public HiBase setToastDefaultGravity(int gravity, int xOffset, int yOffset) {
        HiToastUtils.setDefaultGravity(gravity, xOffset, yOffset);
        return this;
    }

    /**
     * 注册activity生命回调
     *
     * @param activityLifecycleCallbacks activity生命周期管理
     * @return MCFramework
     */
    public HiBase registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        mActivityLifecycleCallbacks = activityLifecycleCallbacks;
        return this;
    }
}
