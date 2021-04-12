package com.jye.hiandroid.base.imageloader;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.jye.hiandroid.base.HiBase;
import com.jye.hiandroid.base.imageloader.builder.DownloadBuilder;
import com.jye.hiandroid.base.imageloader.builder.ImageLoadBuilder;
import com.jye.hiandroid.base.imageloader.request.DownloadRequest;
import com.jye.hiandroid.base.imageloader.request.ImageLoadRequest;

import java.io.File;

/**
 * 图片加载器抽象
 *
 * @author jye
 * @since 1.0
 */
public final class HiImageLoader {

    private static HiImageLoader sInstance;

    private IEngine mGlobalLoader;

    /**
     * 初始化 MCImageLoader
     *
     * @param engine 全局(默认)的加载器实现
     */
    public static HiImageLoader init(IEngine engine) {
        sInstance = new HiImageLoader(engine);
        return sInstance;
    }

    /**
     * 获取 MCImageLoader 实例
     *
     * @return MCImageLoader
     */

    public static HiImageLoader getInstance() {
        checkInitialized();
        return sInstance;
    }

    /**
     * 检查初始化状态
     */
    private static void checkInitialized() {
        if (sInstance == null) {
            throw new IllegalStateException("please init MCImageLoader");
        }
    }

    /**
     * 构造函数
     *
     * @param globalLoader 全局（默认）加载器引擎
     */
    private HiImageLoader(IEngine globalLoader) {
        this.mGlobalLoader = globalLoader;
    }

    /**
     * 实际的加载器引擎
     */
    public interface IEngine {

        /**
         * 加载图片
         *
         * @param request 加载请求参数
         */
        void loadImage(@NonNull ImageLoadRequest request);

        /**
         * 下载图片
         *
         * @param request 下载请求参数
         */
        void download(@NonNull DownloadRequest request);
    }


    /**
     * 获取图片加载器策略
     *
     * @return 图片加载器策略实现
     */
    public IEngine getEngine() {
        return mGlobalLoader;
    }

    /**
     * 加载图片
     *
     * @param uri Uri对象
     * @return ImageLoadBuilder
     */
    public ImageLoadBuilder load(Uri uri) {
        return new ImageLoadBuilder(HiBase.getInstance().getAppContext(), uri);
    }

    /**
     * 加载图片
     *
     * @param uriString uri字符串（如：http://xxx.jpg等等..）
     * @return ImageLoadBuilder
     */
    public ImageLoadBuilder load(String uriString) {
        return load(Uri.parse(uriString));
    }

    /**
     * 加载图片
     *
     * @param file 图片文件
     * @return ImageLoadBuilder
     */
    public ImageLoadBuilder load(File file) {
        return load(Uri.fromFile(file));
    }

    /**
     * 加载图片
     *
     * @param imageResId 本地图片资源id
     * @return ImageLoadBuilder
     */
    public ImageLoadBuilder load(int imageResId) {
        return load(Uri.parse("android.resource://" + HiBase.getInstance().getAppContext().getPackageName() + "/" + imageResId));
    }

    /**
     * 下载图片
     *
     * @param url 图片url地址
     * @return 图片下载构建器
     */
    public DownloadBuilder download(String url) {
        return new DownloadBuilder(HiBase.getInstance().getAppContext(), url);
    }

}
