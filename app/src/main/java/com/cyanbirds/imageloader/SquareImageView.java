package com.cyanbirds.imageloader;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 作者：wangyb
 * 时间：2017/1/20 18:04
 * 描述：
 */
public class SquareImageView extends ImageView {
	public SquareImageView(Context context) {
		super(context);
	}

	public SquareImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}
}
