package com.jye.hiandroid.base.imageloader.builder;

import android.content.Context;

import com.jye.hiandroid.base.imageloader.HiImageLoader;
import com.jye.hiandroid.base.imageloader.callback.DownloadProgressListener;
import com.jye.hiandroid.base.imageloader.request.DownloadRequest;

/**
 * 图片下载构建器
 *
 * @author jye
 * @since 1.0
 */
public class DownloadBuilder {

    private DownloadRequest request;

    public DownloadBuilder(Context context, String url) {
        request = new DownloadRequest();
        request.context = context;
        request.url = url;
    }

    /**
     * 设置文件保存路径
     *
     * @param dirPath 文件保存目录路径
     * @return DownloadBuilder
     */
    public DownloadBuilder savePath(String dirPath) {
        request.dirPath = dirPath;
        return this;
    }

    /**
     * 设置文件保存路径
     *
     * @param dirPath  文件保存目录路径
     * @param fileName 文件名
     * @return DownloadBuilder
     */
    public DownloadBuilder savePath(String dirPath, String fileName) {
        request.dirPath = dirPath;
        request.fileName = fileName;
        return this;
    }

    /**
     * 下载完成后是否需要保存到相册，默认不保存
     *
     * @param isIntoAlbum 是否需要保存到相册
     * @return DownloadBuilder
     */
    public DownloadBuilder intoAlbum(boolean isIntoAlbum) {
        request.isIntoAlbum = isIntoAlbum;
        return this;
    }

    /**
     * 执行下载
     *
     * @param listener 下载回调监听器
     */
    public void execute(DownloadProgressListener listener) {
        request.listener = listener;
        HiImageLoader.getInstance().getEngine().download(request);
    }

}
