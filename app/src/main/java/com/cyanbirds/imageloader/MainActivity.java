package com.cyanbirds.imageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.GridView;

import java.util.ArrayList;
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
		List<String> list = new ArrayList<>();
		list.add("http://pic36.nipic.com/20131128/11748057_141932278338_2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_a5a3f9c6-b008-4f82-9f8d-4cb3611cb92d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_628b5edb-9cbe-42aa-aaa5-8a2570befcf7.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_9911fdd8-afa4-4aa5-844c-046a84116d5f.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_41796363-ecb8-4da5-8c9b-5a31b3647cc2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_321262f4-28c1-48be-ba4b-3889acda945d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_ac1eb2ba-d6b6-4f24-8145-c9c97dd38dac.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_d0be4994-df39-49b7-9b08-e58e9b1d3099.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_039d008a-1708-4508-bca0-99b5dd03e848.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_2c9a64eb-1ccd-42b2-bdd5-4734577867cd.jpg");
		list.add("http://pic36.nipic.com/20131128/11748057_141932278338_2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_a5a3f9c6-b008-4f82-9f8d-4cb3611cb92d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_628b5edb-9cbe-42aa-aaa5-8a2570befcf7.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_9911fdd8-afa4-4aa5-844c-046a84116d5f.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_41796363-ecb8-4da5-8c9b-5a31b3647cc2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_321262f4-28c1-48be-ba4b-3889acda945d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_ac1eb2ba-d6b6-4f24-8145-c9c97dd38dac.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_d0be4994-df39-49b7-9b08-e58e9b1d3099.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_039d008a-1708-4508-bca0-99b5dd03e848.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_2c9a64eb-1ccd-42b2-bdd5-4734577867cd.jpg");
		list.add("http://pic36.nipic.com/20131128/11748057_141932278338_2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_a5a3f9c6-b008-4f82-9f8d-4cb3611cb92d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_628b5edb-9cbe-42aa-aaa5-8a2570befcf7.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_9911fdd8-afa4-4aa5-844c-046a84116d5f.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_41796363-ecb8-4da5-8c9b-5a31b3647cc2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_321262f4-28c1-48be-ba4b-3889acda945d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_ac1eb2ba-d6b6-4f24-8145-c9c97dd38dac.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_d0be4994-df39-49b7-9b08-e58e9b1d3099.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_039d008a-1708-4508-bca0-99b5dd03e848.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_2c9a64eb-1ccd-42b2-bdd5-4734577867cd.jpg");
		list.add("http://pic36.nipic.com/20131128/11748057_141932278338_2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_a5a3f9c6-b008-4f82-9f8d-4cb3611cb92d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_628b5edb-9cbe-42aa-aaa5-8a2570befcf7.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_9911fdd8-afa4-4aa5-844c-046a84116d5f.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_41796363-ecb8-4da5-8c9b-5a31b3647cc2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_321262f4-28c1-48be-ba4b-3889acda945d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_ac1eb2ba-d6b6-4f24-8145-c9c97dd38dac.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_d0be4994-df39-49b7-9b08-e58e9b1d3099.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_039d008a-1708-4508-bca0-99b5dd03e848.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_2c9a64eb-1ccd-42b2-bdd5-4734577867cd.jpg");
		list.add("http://pic36.nipic.com/20131128/11748057_141932278338_2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_a5a3f9c6-b008-4f82-9f8d-4cb3611cb92d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_628b5edb-9cbe-42aa-aaa5-8a2570befcf7.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_9911fdd8-afa4-4aa5-844c-046a84116d5f.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_41796363-ecb8-4da5-8c9b-5a31b3647cc2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_321262f4-28c1-48be-ba4b-3889acda945d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_ac1eb2ba-d6b6-4f24-8145-c9c97dd38dac.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_d0be4994-df39-49b7-9b08-e58e9b1d3099.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_039d008a-1708-4508-bca0-99b5dd03e848.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_2c9a64eb-1ccd-42b2-bdd5-4734577867cd.jpg");
		list.add("http://pic36.nipic.com/20131128/11748057_141932278338_2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_a5a3f9c6-b008-4f82-9f8d-4cb3611cb92d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_628b5edb-9cbe-42aa-aaa5-8a2570befcf7.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_9911fdd8-afa4-4aa5-844c-046a84116d5f.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_41796363-ecb8-4da5-8c9b-5a31b3647cc2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_321262f4-28c1-48be-ba4b-3889acda945d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_ac1eb2ba-d6b6-4f24-8145-c9c97dd38dac.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_d0be4994-df39-49b7-9b08-e58e9b1d3099.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_039d008a-1708-4508-bca0-99b5dd03e848.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_2c9a64eb-1ccd-42b2-bdd5-4734577867cd.jpg");
		list.add("http://pic36.nipic.com/20131128/11748057_141932278338_2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_a5a3f9c6-b008-4f82-9f8d-4cb3611cb92d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_628b5edb-9cbe-42aa-aaa5-8a2570befcf7.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_9911fdd8-afa4-4aa5-844c-046a84116d5f.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_41796363-ecb8-4da5-8c9b-5a31b3647cc2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_321262f4-28c1-48be-ba4b-3889acda945d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_ac1eb2ba-d6b6-4f24-8145-c9c97dd38dac.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_d0be4994-df39-49b7-9b08-e58e9b1d3099.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_039d008a-1708-4508-bca0-99b5dd03e848.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_2c9a64eb-1ccd-42b2-bdd5-4734577867cd.jpg");
		list.add("http://pic36.nipic.com/20131128/11748057_141932278338_2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_a5a3f9c6-b008-4f82-9f8d-4cb3611cb92d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_628b5edb-9cbe-42aa-aaa5-8a2570befcf7.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_9911fdd8-afa4-4aa5-844c-046a84116d5f.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_41796363-ecb8-4da5-8c9b-5a31b3647cc2.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_321262f4-28c1-48be-ba4b-3889acda945d.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_ac1eb2ba-d6b6-4f24-8145-c9c97dd38dac.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_d0be4994-df39-49b7-9b08-e58e9b1d3099.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_039d008a-1708-4508-bca0-99b5dd03e848.jpg");
		list.add("http://real-love-server.img-cn-shenzhen.aliyuncs.com/tan_love/img/tl_2c9a64eb-1ccd-42b2-bdd5-4734577867cd.jpg");
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
