package com.cyanbirds.library;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 作者：wangyb
 * 时间：2017/1/20 16:52
 * 描述：异步加载图片的封装类
 */
public class LoaderResult {
	public Bitmap mBitmap;//从缓存或者网络加载的图片
	public ImageView mImageView;//图片控件
	public String mUrl;//图片的url
}
