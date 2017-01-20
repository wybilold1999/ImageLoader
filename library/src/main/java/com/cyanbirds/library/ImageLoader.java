package com.cyanbirds.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：wangyb
 * 时间：2017/1/20 15:02
 * 描述：
 */
public class ImageLoader {

	private static final String TAG = "ImageLoader";
	private static ImageLoader mInstance;
	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	private static final int CORE_POOL_COUNT = CPU_COUNT + 1;
	private static final int MAXIMUM_POOL_COUNT = CPU_COUNT * 2 + 1;
	private static final long KEEP_ALIVE = 10L;

	public static final int TAG_KEY_URI = 0;

	private static final long DIS_CACHE_SIZE = 1024 * 1024 * 50;//50M
	private static final int IO_BUFFER_SIZE = 1024 * 8;
	private static final int DIS_CACHE_INDEX = 0;
	private static boolean mIsDiskCacheCreated = false;

	private static final int MSG_POST_RESULT = 1;

	private Context mContext;
	private ImageResizer mImageResizer = new ImageResizer();
	private LruCache<String, Bitmap> mMemoryCache;//内存缓存
	private DiskLruCache mDiskLruCache;//磁盘缓存

	/**
	 * 初始化线程池
	 */
	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
		}
	};

	private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
			CORE_POOL_COUNT, MAXIMUM_POOL_COUNT, KEEP_ALIVE, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>(), sThreadFactory);


	private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			LoaderResult loaderResult = (LoaderResult) msg.obj;
			ImageView imageView = loaderResult.mImageView;
			Bitmap bitmap = loaderResult.mBitmap;
			imageView.setImageBitmap(bitmap);
		}
	};

	/**
	 * 在构造方法中初始化内存缓存和磁盘缓存
	 * @param context
	 */
	public ImageLoader(Context context) {
		mContext = context.getApplicationContext();
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		int mCacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(mCacheSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight() / 1024;
			}
		};

		File diskCacheDir = getDiskCacheDir(mContext, "bitmap");
		if (!diskCacheDir.exists()) {
			diskCacheDir.mkdir();
		}
		if (getUsableSpace(diskCacheDir) > DIS_CACHE_SIZE) {
			try {
				mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DIS_CACHE_SIZE);
				mIsDiskCacheCreated = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将bitmap加入内存缓存
	 * @param key
	 * @param bitmap
	 */
	private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemoryCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * 从内存缓存中取出bitmap
	 * @param key
	 * @return
	 */
	private Bitmap getBitmapFromMemoryCache(String key) {
		return mMemoryCache.get(key);
	}

	/**
	 * 从磁盘缓存中获取指定大小的bitmap，并将指定大小的bitmap又加入内存缓存
	 * @param key
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * @throws IOException
	 */
	private Bitmap getBitmapFromDiskCache(String key, int reqWidth, int reqHeight) throws IOException {
		if (Looper.myLooper() == Looper.getMainLooper()) {
			Log.w(TAG, "load bitmap from UI Thread, it's not recommended!");
		}
		if (mDiskLruCache == null) {
			return null;
		}

		Bitmap bitmap = null;
		DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
		if (snapshot != null) {
			FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DIS_CACHE_INDEX);
			FileDescriptor fileDescriptor = fileInputStream.getFD();
			bitmap = mImageResizer.decodeBitmapFromFileDescriptor(
					mContext, fileDescriptor, reqWidth, reqHeight);
			if (bitmap != null) {
				addBitmapToMemoryCache(key, bitmap);
			}
		}
		return bitmap;
	}


	/**
	 * 异步加载图片
	 * 加载图片始终遵循的规则：内存缓存->磁盘缓存->网络
	 * @param imageView
	 * @param url
	 */
	private void loadBitmapAsync(final ImageView imageView, final String url, final int reqWidth, final int reqHeight) {
		Bitmap bitmap = getBitmapFromMemoryCache(Md5Util.md5(url));
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			return;
		}

		Runnable loadBitmapTask = new Runnable() {
			@Override
			public void run() {
				Bitmap bitmap = loadBitmapSync(url, reqWidth, reqHeight);
				if (bitmap != null) {
					LoaderResult loaderResult = new LoaderResult();
					loaderResult.mBitmap = bitmap;
					loaderResult.mImageView = imageView;
					loaderResult.mUrl = url;
					mMainHandler.obtainMessage(MSG_POST_RESULT, loaderResult).sendToTarget();
				}
			}
		};
		THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
	}

	/**
	 * 同步加载图片
	 * 加载图片始终遵循的规则：内存缓存->磁盘缓存->网络
	 * @param url
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private Bitmap loadBitmapSync(String url, int reqWidth, int reqHeight) {
		Bitmap bitmap = getBitmapFromMemoryCache(Md5Util.md5(url));
		if (bitmap != null) {
			Log.d(TAG, "load bitmap from memory, url=" + url);
			return bitmap;
		}
		try {
			bitmap = getBitmapFromDiskCache(Md5Util.md5(url), reqWidth, reqHeight);
			if (bitmap != null) {
				Log.d(TAG, "load bitmap from diskCache, url=" + url);
				return bitmap;
			}
			bitmap = loadBitmapFromHttp(url, reqWidth, reqHeight);
			Log.d(TAG, "load bitmap from network, url=" + url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bitmap == null && mIsDiskCacheCreated == false) {
			Log.w(TAG, "encounter error, DiskCache not create");
			bitmap = downloadBitmapFromHttp(url);
		}
		return bitmap;
	}

	/**
	 * 从网络中获取图片，并将获取的图片放入磁盘缓存
	 * @param uri
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * @throws IOException
	 */
	private Bitmap loadBitmapFromHttp(String uri, int reqWidth, int reqHeight) throws IOException {
		if (Looper.myLooper() == Looper.getMainLooper()) {
			throw new RuntimeException("Can not visit network from UI thread.");
		}

		if (mDiskLruCache != null) {
			DiskLruCache.Editor editor = mDiskLruCache.edit(Md5Util.md5(uri));
			if (editor != null) {
				OutputStream outputStream = editor.newOutputStream(DIS_CACHE_INDEX);
				if (downloadUrlToDiskStream(uri, outputStream)) {
					editor.commit();
				} else {
					editor.abort();
				}
			}
			mDiskLruCache.flush();
		}
		return getBitmapFromDiskCache(Md5Util.md5(uri),reqWidth, reqHeight);
	}

	/**
	 * 将网络中的图片通过流的形式写入磁盘缓存
	 * @param uri
	 * @param outputStream
	 * @return
	 */
	private boolean downloadUrlToDiskStream(String uri, OutputStream outputStream) {
		HttpURLConnection urlConnection = null;
		BufferedOutputStream bufferedOutputStream = null;
		BufferedInputStream bufferedInputStream = null;

		try {
			URL url = new URL(uri);
			urlConnection = (HttpURLConnection) url.openConnection();
			bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
			bufferedOutputStream = new BufferedOutputStream(outputStream);

			int b;
			while ((b = bufferedInputStream.read()) != -1) {
				bufferedOutputStream.write(b);
			}
			return true;
		} catch (IOException e) {
			Log.e(TAG, "download from network failed, e=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			try {
				if (bufferedInputStream != null) {
					bufferedInputStream.close();
				}
				if (bufferedOutputStream != null) {
					bufferedOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 下载图片，直接返回bitmap，不放入缓存(因为通过loadBitmapFromHttp这个方法判断缓存还没有创建)
	 * @param uri
	 * @return
	 */
	private Bitmap downloadBitmapFromHttp(String uri) {
		HttpURLConnection urlConnection = null;
		BufferedInputStream bufferedInputStream = null;

		try {
			URL url = new URL(uri);
			urlConnection = (HttpURLConnection) url.openConnection();
			bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
			Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
			return bitmap;
		} catch (IOException e) {
			Log.e(TAG, "download from network failed, e=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			try {
				if (bufferedInputStream != null) {
					bufferedInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}



	/**
	 * 获取缓存的目录
	 * @param context
	 * @param dirName
	 * @return
	 */
	private File getDiskCacheDir(Context context, String dirName) {
		boolean externalStorageAvailable = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED
		);
		String cachePath = "";
		if (externalStorageAvailable) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + dirName);
	}

	/**
	 * 当前目录可利用空间
	 * @param path
	 * @return
	 */
	private long getUsableSpace(File path) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			return path.getUsableSpace();
		}
		final StatFs statFs = new StatFs(path.getPath());
		return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
	}

	public static ImageLoader getInstance(Context context) {
		if (mInstance == null) {
			synchronized (ImageLoader.class) {
				if (mInstance == null) {
					mInstance = new ImageLoader(context);
				}
			}
		}
		return mInstance;
	}



	/******************************************************对外的接口*****************************************************/

	/**
	 * 异步加载图片
	 * @param url
	 * @param imageView
	 * @param reqWidth
	 * @param reqHeight
	 */
	public void displayImageAsync(String url, ImageView imageView, int reqWidth, int reqHeight) {
		imageView.setTag(url);
		loadBitmapAsync(imageView, url, reqWidth, reqHeight);
	}

	/**
	 * 同步加载图片
	 * @param url
	 * @param imageView
	 * @param reqWidth
	 * @param reqHeight
	 */
	public void displayImageSync(String url, ImageView imageView, int reqWidth, int reqHeight) {
		imageView.setImageBitmap(loadBitmapSync(url, reqWidth, reqHeight));
	}

}
