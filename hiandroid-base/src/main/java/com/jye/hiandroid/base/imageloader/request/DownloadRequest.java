package com.jye.hiandroid.base.imageloader.request;

import android.content.Context;

import com.jye.hiandroid.base.imageloader.callback.DownloadProgressListener;

/**
 * 下载请求参数配置
 *
 * @author jye
 * @since 1.0
 */
public class DownloadRequest {

    /**
     * 上下文对象
     */
    public Context context;

    /**
     * 图片下载地址
     */
    public String url;

    /**
     * 文件保存目录地址
     */
    public String dirPath;

    /**
     * 文件名
     */
    public String fileName;


    /**
     * 是否需要保存到相册
     */
    public boolean isIntoAlbum;

    /**
     * 下载进度监听器
     */
    public DownloadProgressListener listener;

}
