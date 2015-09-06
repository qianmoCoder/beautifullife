package com.dream.beautifullife.network;

/**
 * 创建日期：2015年9月6日 版权所有 悦畅科技有限公司。 保留所有权利。<br>
 * 项目名：悟空停车 - Android客户端<br>
 * 描述：
 * 
 * @author admin
 */
public interface ProgressRequestListener {

	void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
