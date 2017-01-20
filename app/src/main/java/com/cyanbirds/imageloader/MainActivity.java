package com.cyanbirds.imageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private GridView mGridView;
	private ImageAdapter mAdapter;
	private boolean mIsGridViewIdle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mGridView = (GridView) findViewById(R.id.gv_imgs);
		List<String> list = Arrays.asList(Images.imageThumbUrls);
		mAdapter = new ImageAdapter(this, list, mIsGridViewIdle);
		mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
					mIsGridViewIdle = true;
					mAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

			}
		});
		mGridView.setAdapter(mAdapter);
	}
}
