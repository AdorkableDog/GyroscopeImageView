# 仿蚂蚁森林实景地图展示


### 项目描述

最近项目里需要做一个农副产品的实景生长环境实时监控的效果，公司购买了设备，由设备厂商提供后台的SDK 获取试试拍摄到图片，然后下发的app.通过后天给出的照片效果发现，居然是全景图片 设计给出的效果图居然和蚂蚁森林里面的实景看林场差不多。然后就开始研究怎么去实现，下面是做出来的效果。

## 效果

![图片描述](https://raw.githubusercontent.com/AdorkableDog/GyroscopeImageView/master/screenshot/screenshot.jpg)

## 具体代码使用

	在onCreate 方法里初始化控件

		@Override
		protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.activity_main);
		  GyroscopeImageView gyroscopeIv = (GyroscopeImageView) findViewById(R.id.vr_pano);

		}

	在onPause执行unregister();
		@Override
		protected void onPause() {
			super.onPause();
			GyroscopeManager.getInstance().unregister(this);
		}

	在onResume执行register();
		@Override
		protected void onResume() {
			super.onResume();
			GyroscopeManager.getInstance().register(this);
		}

		···
		方法里执行
		
		public void ***（）{
	 		gyroscopeIv.post(new Runnable() {
				@Override
				public void run() {
					Picasso.with(MainActivity.this)
							.load(imageData.get(0))
							.config(Bitmap.Config.RGB_565)
							//.load(PIC3_URL)
							.into(gyroscopeIv);
				}
			});
		}

		图片还是使用了第三方的图片展示，不过显示的imageView 做了自定义，能够相应陀螺仪和手势事件（此处做了处理）具体代码在
		GyroscopeImageView.java 和 GyroscopeManager.java 两个类中，有兴趣的小伙伴可以看看源码了解一下
##

	





