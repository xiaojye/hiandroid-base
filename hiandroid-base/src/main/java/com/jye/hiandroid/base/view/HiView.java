package com.jye.hiandroid.base.view;

import android.content.Context;
import android.os.Bundle;

/**
 * Activity/Fragment的通用功能抽象
 *
 * @author jye
 * @since 1.0
 */
public interface HiView {

    /**
     * 获取上下文对象
     *
     * @return 上下文对象
     */
    Context getContext();

    /**
     * 显示短时间的Toast
     *
     * @param message 提示文字
     */
    void showShortToast(String message);

    /**
     * 显示长时间的Toast
     *
     * @param message 提示文字
     */
    void showLongToast(String message);

    /**
     * 界面跳转
     *
     * @param cls 目标Activity
     */
    void startActivity(Class<?> cls);

    /**
     * 跳转界面，传参
     *
     * @param cls    目标Activity
     * @param bundle 数据
     */
    void startActivity(Class<?> cls, Bundle bundle);

    /**
     * 跳转界面并关闭当前界面
     *
     * @param cls 目标Activity
     */
    void startActivityAndKillSelf(Class<?> cls);

    /**
     * @param cls    目标Activity
     * @param bundle 数据
     */
    void startActivityAndKillSelf(Class<?> cls, Bundle bundle);

    /**
     * startActivityForResult
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     */
    void startActivityForResult(Class<?> cls, int requestCode);

    /**
     * startActivityForResult with bundle
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    void startActivityForResult(Class<?> cls, int requestCode, Bundle bundle);
}
