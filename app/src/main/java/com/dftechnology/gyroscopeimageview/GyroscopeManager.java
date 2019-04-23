package com.dftechnology.gyroscopeimageview;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GyroscopeManager implements SensorEventListener {

	private static final String TAG = GyroscopeManager.class.getSimpleName();

	/**
	 * 将纳秒转化为秒
	 */
	private static final float NS2S = 1.0f / 1000000000.0f;

	/**
	 * 维护 GyroscopeImageView 的 状态, 需要传感器处理的 GyroscopeImageView 其对应的 value 为 true
	 */
	private Map<GyroscopeImageView, Boolean> mViewsMap = new HashMap<>(9);

	/**
	 * 维护需要传感器处理的 Activity
	 */
	private List<Activity> mActivityList = new ArrayList<>();

	private SensorManager sensorManager;
	private long mLastTimestamp;
	private double mMaxAngle = Math.PI / 2;

	private GyroscopeManager() {
	}

	private static class InstanceHolder {
		private static final GyroscopeManager sGyroscopeManager = new GyroscopeManager();
	}

	public static GyroscopeManager getInstance() {
		return InstanceHolder.sGyroscopeManager;
	}

	protected void addView(GyroscopeImageView gyroscopeImageView) {
		if (mActivityList.contains(getActivityFromView(gyroscopeImageView))) {
			mViewsMap.put(gyroscopeImageView, true);
		} else {
			mViewsMap.put(gyroscopeImageView, false);
		}
	}

	protected void removeView(GyroscopeImageView gyroscopeImageView) {
		mViewsMap.remove(gyroscopeImageView);
	}

	public void register(Activity activity) {
		mActivityList.add((activity));
		for (GyroscopeImageView view : mViewsMap.keySet()) {
			for (Activity a : mActivityList) {
				if (getActivityFromView(view) == a) {
					mViewsMap.put(view, true);
				}
			}
		}

		if (sensorManager == null) {
			sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
		}

		Sensor sensor;

		if (sensorManager != null) {
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
			//灵敏度从快到慢 可选择: SENSOR_DELAY_FASTEST; SENSOR_DELAY_GAME; SENSOR_DELAY_NORMAL; SENSOR_DELAY_UI
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
			mLastTimestamp = 0;

		}
	}

	public void unregister(Activity activity) {
		mActivityList.remove((activity));
		for (GyroscopeImageView view : mViewsMap.keySet()) {
			if (getActivityFromView(view) == activity) {
				mViewsMap.put(view, false);
			}
		}
		sensorManager.unregisterListener(this);
		sensorManager = null;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			if (mLastTimestamp != 0) {
				for (Map.Entry<GyroscopeImageView, Boolean> entry : mViewsMap.entrySet()) {
					//只处理集合中 value 为 true 的 view
					if (entry.getValue()) {
//						entry.getKey().mAngleX += event.values[0] * (event.timestamp - mLastTimestamp) * NS2S * 2.0f;
						float x = event.values[1] * (event.timestamp - mLastTimestamp) * NS2S * 2.0f;
						entry.getKey().update(x, x);
					}
				}
			}
			mLastTimestamp = event.timestamp;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	private Activity getActivityFromView(View view) {
		return (Activity) view.getContext();
	}
}
