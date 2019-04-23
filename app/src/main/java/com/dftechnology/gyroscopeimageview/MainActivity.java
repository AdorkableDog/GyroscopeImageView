package com.dftechnology.gyroscopeimageview;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


	private List<String> imageData = new ArrayList<>();
	private GyroscopeImageView gyroscopeIv;
	private RecyclerView mRecycleerView;
	private HotRecommAdapter hotRecommAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gyroscopeIv = (GyroscopeImageView) findViewById(R.id.vr_pano);
		mRecycleerView = (RecyclerView) findViewById(R.id.recyclerView);

		LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
		mRecycleerView.setLayoutManager(layoutManager);

		imageData.add("http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1010/09/c1/5445799_5445799_1286585762968.jpg");
		imageData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556000840641&di=817c5729ea1b8093405008472f2fe4d3&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01cf1055437dd90000019ae9b0e8c5.jpg");


		hotRecommAdapter = new HotRecommAdapter(MainActivity.this, imageData);
		mRecycleerView.setAdapter(hotRecommAdapter);




		gyroscopeIv.post(new Runnable() {
			@Override
			public void run() {
				Picasso.with(MainActivity.this)
						.load(imageData.get(0))
						.config(Bitmap.Config.RGB_565)
//						.load(PIC3_URL)
						.into(gyroscopeIv);
			}
		});

		hotRecommAdapter.setOnItemClickListener(new HotRecommAdapter.ProfitDetialClickListener() {
			@Override
			public void onItemClick(View view, int postion) {
				initDatas(imageData.get(postion));
			}
		});

	}

	private void initDatas(final String s) {
		gyroscopeIv.post(new Runnable() {
			@Override
			public void run() {
				Picasso.with(MainActivity.this)
//						.load(dataList.get(0).getLiveingUrl())
						.load(s)
						.config(Bitmap.Config.RGB_565)
						.into(gyroscopeIv);
			}
		});
	}


	@Override
	protected void onPause() {
		super.onPause();
		GyroscopeManager.getInstance().unregister(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		GyroscopeManager.getInstance().register(this);
	}
}
