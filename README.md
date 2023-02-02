# 仿蚂蚁森林实景地图展示


### 项目描述

最近项目里需要做一个农副产品的实景生长环境实时监控的效果，公司购买了设备，由设备厂商提供给后台的SDK 获取拍摄到图片，然后调接口下放到app.通过后台给出的照片效果发现，居然是全景图片 设计给出的效果图居然和蚂蚁森林里面的实景看林场差不多。然后就开始研究怎么去实现，下面是做出来的效果。

## 效果

![图片描述](https://raw.githubusercontent.com/AdorkableDog/GyroscopeImageView/master/screenshot/SVID_20190423_170935_1.gif)

 GIF图显示和真实显示效果有所差距， 实际效果为手势左右滑动加手机左右旋转。

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
## License

    Copyright 2016 
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

## 如果喜欢，还请start&Fork&follow进行支持，谢谢O(∩_∩)O~。

	





