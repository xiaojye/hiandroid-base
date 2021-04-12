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
import androidx.fragment.app.Fragment;

import com.jye.hiandroid.util.HiToastUtils;

/**
 * Fragment基类
 *
 * @author jye
 * @since 1.0
 */
public abstract class HiFragment extends Fragment implements HiView, Backable ,View.OnClickListener{

    protected Context mContext;
    protected Activity mActivity;
    protected LayoutInflater mLayoutInflater;

    @Nullable
    protected Bundle mSavedInstanceState;

    @Nullable
    protected View mContentView;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mContext = activity;
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutInflater = LayoutInflater.from(getActivity());
        mSavedInstanceState = savedInstanceState;
        if (getArguments() != null) {
            handleArgs(getArguments());
        }
        if (null != mContentView) {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (null != parent) {
                parent.removeView(mContentView);
            }
        } else {
            Object contentView = getContentView(mSavedInstanceState);
            if (contentView instanceof View) {
                mContentView = (View) contentView;
            } else if (contentView instanceof Integer) {
                mContentView = View.inflate(mContext, (Integer) contentView, null);
            }
        }
        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(mSavedInstanceState);
        initData(mSavedInstanceState);
    }

    /**
     * 处理参数
     *
     * @param args getArguments()
     */
    protected void handleArgs(@NonNull Bundle args) {
    }

    /**
     * 获取内容视图
     *
     * @return View，Int；返回Int表示layoutId
     */
    @Nullable
    protected abstract Object getContentView(@Nullable Bundle savedInstanceState);

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
    public boolean onBackPressed() {
        return BackableHelper.handleBackPress(this);
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
        Intent intent = new Intent(getContext(), cls);
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
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    /**
     * startActivityForResult
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     */
    @Override
    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(getContext(), cls);
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
        Intent intent = new Intent(getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
