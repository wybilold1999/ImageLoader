package com.cyanbirds.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;

/**
 * 作者：wangyb
 * 时间：2017/1/20 14:16
 * 描述：压缩图片
 */
public class ImageResizer {
	public static final String TAG = "ImageResizer";

	public ImageResizer() {

	}

	/**
	 * 从资源中加载图片
	 * @param context
	 * @param resId 图片资源id
	 * @param reqWidth 指定图片的宽度
	 * @param reqHeight 指定图片的高度
	 * @return
	 */
	public Bitmap decodeBitmapFromResource(Context context, int resId, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//标识只获取图片的宽高，不会加载图片
		BitmapFactory.decodeResource(context.getResources(), resId, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(context.getResources(), resId, options);
	}

	/**
	 * 从文件流中加载图片
	 * 由于FileInputStream是有顺序流，两次decodeStream会导致流的位置属性发生变化
	 * 于是第二次的decodeStream获取就为null，所以通过文件描述符从文件中获取bitmap
	 * @param context
	 * @param fd
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public Bitmap decodeBitmapFromFileDescriptor(Context context, FileDescriptor fd,
												 int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//标识只获取图片的宽高，不会加载图片
		BitmapFactory.decodeFileDescriptor(fd, null, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFileDescriptor(fd, null, options);
	}

	/**
	 * 将指定的bitmap对象进行压缩
	 * @param bitmap
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public Bitmap resizeBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] datas = baos.toByteArray();

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//标识只获取图片的宽高，不会加载图片
		BitmapFactory.decodeByteArray(datas, 0, datas.length, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(datas, 0, datas.length, options);
	}

	/**
	 * 计算压缩率
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		int inSampleSize = 1;//压缩率
		int width = options.outWidth;
		int height = options.outHeight;
		Log.d(TAG, "original, w=" + width + " h=" + height);
		if (width == 0 || height == 0) {
			return inSampleSize;
		}

		if (width > reqWidth || height >reqHeight) {
			int halfWidth = options.outWidth / 2;
			int halfHeight = options.outHeight / 2;

			while ((halfWidth / inSampleSize) > reqWidth || (halfHeight / inSampleSize) > reqHeight) {
				inSampleSize *= 2;
			}
		}
		Log.d(TAG, "inSampleSize=" + inSampleSize);
		return inSampleSize;
	}
}
