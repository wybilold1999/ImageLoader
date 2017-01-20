package com.cyanbirds.imageloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cyanbirds.library.ImageLoader;

import java.util.List;

/**
 * 作者：wangyb
 * 时间：2017/1/20 18:06
 * 描述：
 */
public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private List<String> mImgList;
	private boolean mIsDle;

	public ImageAdapter(Context context, List<String> imgsList, boolean isDle) {
		mImgList = imgsList;
		mContext = context;
		mIsDle = isDle;
	}
	@Override
	public int getCount() {
		return mImgList.size();
	}

	@Override
	public Object getItem(int position) {
		return mImgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image, null);
			holder = new ViewHolder();
			holder.mImageView = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance(mContext).displayImageAsync(
				mImgList.get(position), holder.mImageView,
				100, 100);
		return convertView;
	}

	public static class ViewHolder {
		ImageView mImageView;
	}
}
