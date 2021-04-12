package com.jye.hiandroid.base.imageloader.builder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.jye.hiandroid.base.imageloader.HiImageLoader;
import com.jye.hiandroid.base.imageloader.request.ImageLoadRequest;

/**
 * 图片加载构建器
 *
 * @author jye
 * @since 1.0
 */
public class ImageLoadBuilder {

    private ImageLoadRequest request;

    public ImageLoadBuilder(Context context, Uri uri) {
        request = new ImageLoadRequest();
        request.context = context;
        request.uri = uri;
    }

    /**
     * 设置占位图资源id
     *
     * @param placeholderResId 占位图资源id
     * @return ImageLoadBuilder
     */
    public ImageLoadBuilder placeholder(int placeholderResId) {
        request.placeholderResId = placeholderResId;
        return this;
    }

    /**
     * 设置占位图Drawable
     *
     * @param placeholderDrawable 占位图Drawable
     * @return ImageLoadBuilder
     */
    public ImageLoadBuilder placeholder(Drawable placeholderDrawable) {
        request.placeholderDrawable = placeholderDrawable;
        return this;
    }

    /**
     * 设置错误图资源id
     *
     * @param errorResId 错误图资源id
     * @return ImageLoadBuilder
     */
    public ImageLoadBuilder error(int errorResId) {
        request.errorResId = errorResId;
        return this;
    }

    /**
     * 设置错误图Drawable
     *
     * @param errorDrawable 错误图Drawable
     * @return ImageLoadBuilder
     */
    public ImageLoadBuilder error(Drawable errorDrawable) {
        request.errorDrawable = errorDrawable;
        return this;
    }

    /**
     * 设置图片的宽高
     *
     * @param targetWidth  图片宽度
     * @param targetHeight 图片高度
     * @return ImageLoadBuilder
     */
    public ImageLoadBuilder resize(int targetWidth, int targetHeight) {
        request.targetWidth = targetWidth;
        request.targetHeight = targetHeight;
        return this;
    }

    /**
     * 设置图片缩放类型
     * {@link ImageView.ScaleType}
     *
     * @param scaleType 图片缩放类型
     * @return ImageLoadBuilder
     */
    public ImageLoadBuilder scaleType(ImageView.ScaleType scaleType) {
        request.scaleType = scaleType;
        return this;
    }

    /**
     * 异步加载图片并显示到指定的ImageView
     *
     * @param imageView 显示图片的ImageView
     */
    public void into(ImageView imageView) {
        request.imageView = imageView;
        HiImageLoader.getInstance().getEngine().loadImage(request);
    }

}