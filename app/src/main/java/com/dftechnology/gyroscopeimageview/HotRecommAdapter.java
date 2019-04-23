package com.dftechnology.gyroscopeimageview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/4/23 0023.
 */

public class HotRecommAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


	private Activity mContext = null;
	private List<String> datas = null;

	ProfitDetialClickListener mItemClickListener;

	public HotRecommAdapter(Activity mContext, List<String> datas) {
		this.mContext = mContext;
		this.datas = datas;
	}


	public void setOnItemClickListener(ProfitDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		HotRecommViewHolder hotRecommViewHolder;
		hotRecommViewHolder = new HotRecommViewHolder(LayoutInflater
				.from(mContext).inflate(R.layout.item_home_seckill_item, parent, false), mItemClickListener);
		return hotRecommViewHolder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

		if (holder instanceof HotRecommViewHolder) {
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((HotRecommViewHolder) holder).hotlll.getLayoutParams();
			DisplayMetrics dm = new DisplayMetrics();
			//获取屏幕信息
			mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int screenWidth = dm.widthPixels;
			layoutParams.width = (int) (screenWidth * 0.3); // 宽度设置为屏幕的0.65
			((HotRecommViewHolder) holder).hotlll.setLayoutParams(layoutParams); //使设置好的布局参数应用到控件</pre>


//			((HotRecommViewHolder) holder).tvTitle.setText(datas.get(position).getProduct_name());
//			((HotRecommViewHolder) holder).tvTitle.setText(datas.get(position));

//			((HotRecommViewHolder) holder).tvContent.setText(mList.get(position).getProduct_abstract());
//			((HotRecommViewHolder) holder).tvPrice.setText("￥" + mList.get(position).getProduct_current());

//					.load(URLBuilder.URLBaseHeader + mList.get(position).getProduct_listImg())


			Picasso.with(mContext)
					.load(datas.get(position))
					.error(R.mipmap.default_goods)
					.into(((HotRecommViewHolder) holder).iv);


		}
	}

	@Override
	public int getItemCount() {
		return datas.size();
	}

	class HotRecommViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


		@BindView(R.id.home_hot_tv_title1)
		TextView tvTitle;
		@BindView(R.id.home_hot_iv1)
		ImageView iv;

		@BindView(R.id.home_hot_ll1)
		LinearLayout hotlll;

		private ProfitDetialClickListener mListener;

		public HotRecommViewHolder(View itemView, ProfitDetialClickListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.mListener = listener;
			itemView.setOnClickListener(this);

		}

		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onItemClick(v, getPosition());
			}
		}
	}

	public interface ProfitDetialClickListener {
		void onItemClick(View view, int postion);
	}
}
