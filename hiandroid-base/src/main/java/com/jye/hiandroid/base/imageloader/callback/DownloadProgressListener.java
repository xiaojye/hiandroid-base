package com.jye.hiandroid.base.imageloader.callback;

import androidx.annotation.NonNull;

import com.jye.hiandroid.base.imageloader.request.DownloadRequest;

import java.io.File;

/**
 * 下载进度监听器
 *
 * @author jye
 * @since 1.0
 */
public interface DownloadProgressListener {

    /**
     * 下载进度回调
     * 下载进度算法：BigDecimal.valueOf(currentSize).divide(BigDecimal.valueOf(totalSize), 2, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).toBigInteger().intValue();
     *
     * @param request     下载请求参数
     * @param currentSize 当前下载大小
     * @param totalSize   文件总大小
     * @param progress    下载进度
     */
    void onProgress(@NonNull DownloadRequest request, long currentSize, long totalSize, int progress);

    /**
     * 下载完成回调
     *
     * @param request   下载请求参数
     * @param imageFile 保存的图片文件
     */
    void onComplete(@NonNull DownloadRequest request, @NonNull File imageFile);

    /**
     * 下载失败回调
     *
     * @param request  下载请求参数
     * @param errorMsg 失败信息
     */
    void onFailure(@NonNull DownloadRequest request, @NonNull String errorMsg);

}
