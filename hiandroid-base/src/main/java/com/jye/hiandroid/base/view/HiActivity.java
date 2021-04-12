package com.jye.hiandroid.base.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jye.hiandroid.util.HiToastUtils;

/**
 * Activity基类
 *
 * @author jye
 * @since 1.0
 */
public abstract class HiActivity extends AppCompatActivity implements HiView, View.OnClickListener {

    protected Context mContext;
    protected Activity mActivity;
    protected LayoutInflater mLayoutInflater;

    @Nullable
    protected Bundle mSavedInstanceState;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        mLayoutInflater = LayoutInflater.from(this);
        mSavedInstanceState = savedInstanceState;

        if (getIntent() != null) {
            handleIntent(getIntent());
        }

        Object contentView = getContentView(mSavedInstanceState);
        if (contentView instanceof View) {
            setContentView((View) contentView);
        } else if (contentView instanceof Integer) {
            setContentView(View.inflate(mContext, (Integer) contentView, null));
        }

        initView(mSavedInstanceState);
        initData(mSavedInstanceState);
    }

    /**
     * 处理Intent传递参数
     *
     * @param intent Intent
     */
    protected void handleIntent(@NonNull Intent intent) {
    }

    /**
     * 获取内容视图
     *
     * @return View，Int；返回Int表示layoutId
     */
    @Nullable
    protected Object getContentView(@Nullable Bundle savedInstanceState) {
        return null;
    }

    /**
     * 初始化视图
     */
    protected void initView(@Nullable Bundle savedInstanceState) {
    }

    /**
     * 初始化数据
     */
    protected void initData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onBackPressed() {
        if (!BackableHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 获取上下文对象
     *
     * @return 上下文对象
     */
    @Override
    public Context getContext() {
        return mContext;
    }

    /**
     * 显示短时间的Toast
     *
     * @param message 提示文字
     */
    @Override
    public void showShortToast(String message) {
        HiToastUtils.showShort(getContext(), message);
    }

    /**
     * 显示长时间的Toast
     *
     * @param message 提示文字
     */
    @Override
    public void showLongToast(String message) {
        HiToastUtils.showLong(getContext(), message);
    }

    /**
     * 界面跳转
     *
     * @param cls 目标Activity
     */
    @Override
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 跳转界面，传参
     *
     * @param cls    目标Activity
     * @param bundle 数据
     */
    @Override
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转界面并关闭当前界面
     *
     * @param cls 目标Activity
     */
    @Override
    public void startActivityAndKillSelf(Class<?> cls) {
        startActivityAndKillSelf(cls, null);
    }

    /**
     * @param cls    目标Activity
     * @param bundle 数据
     */
    @Override
    public void startActivityAndKillSelf(Class<?> cls, Bundle bundle) {
        startActivity(cls, bundle);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     */
    @Override
    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    @Override
    public void startActivityForResult(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
