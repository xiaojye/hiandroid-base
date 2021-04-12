package com.jye.hiandroid.base.imageloader.request;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

/**
 * 加载请求参数配置
 *
 * @author jye
 * @since 1.0
 */
public class ImageLoadRequest {

    /**
     * 上下文对象
     */
    public Context context;

    /**
     * 图片Uri
     */
    public Uri uri;

    /**
     * 占位图资源id
     */
    public int placeholderResId;

    /**
     * 占位图drawable
     */
    public Drawable placeholderDrawable;

    /**
     * 错误图资源id
     */
    public int errorResId;

    /**
     * 错误图Drawable
     */
    public Drawable errorDrawable;

    /**
     * 图片宽度
     */
    public int targetWidth;

    /**
     * 图片高度
     */
    public int targetHeight;

    /**
     * 缩放类型
     */
    public ImageView.ScaleType scaleType;

    /**
     * 展示加载的图片的ImageView
     */
    public ImageView imageView;

}
