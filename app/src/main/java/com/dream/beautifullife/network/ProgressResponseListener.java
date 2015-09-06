package com.dream.beautifullife.network;

/**
 * 创建日期：2015年9月6日
 * 描述：响应体进度回调，比如用于文件下载中
 * @author admin
 */
public interface ProgressResponseListener {

	public void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
