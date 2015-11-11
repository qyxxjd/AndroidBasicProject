package com.classic.core.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;


/**
 * 双击退出应用程序
 * @author 续写经典
 * @date 2015/11/3
 */
public class DoubleClickExitHelper {

	/** 两次点击的有效时间间隔，单位：毫秒 */
	private int    TIME_INTERVAL        = 2000;
	private static final String DEFAULT_TOAST_STRING = "再按一次返回键退出应用";
	private final Activity mActivity;
	private boolean isOnKeyBacking;
	private Handler mHandler;
	private Toast mBackToast;

	/**
	 * 设置两次点击的有效时间间隔
	 * @param time 单位：毫秒
	 */
	public DoubleClickExitHelper setTimeInterval(int time){
		TIME_INTERVAL = time;
		return this;
	}

	/**
	 * 设置toast内容
	 * @param resId
	 * @return
	 */
	public DoubleClickExitHelper setToastContent(int resId){
		mBackToast = Toast.makeText(mActivity, resId, Toast.LENGTH_LONG);
		return this;
	}

	/**
	 * 设置toast内容
	 * @param content
	 * @return
	 */
	public DoubleClickExitHelper setToastContent(String content){
		mBackToast = Toast.makeText(mActivity, content, Toast.LENGTH_LONG);
		return this;
	}

  public DoubleClickExitHelper(Activity activity) {
		mActivity = activity;
		mHandler = new Handler(Looper.getMainLooper());
		mBackToast = Toast.makeText(mActivity, DEFAULT_TOAST_STRING, Toast.LENGTH_LONG);
	}

	/**
	 * Activity onKeyDown事件
	 * */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode != KeyEvent.KEYCODE_BACK) {
			return false;
		}
		if(isOnKeyBacking) {
			mHandler.removeCallbacks(onBackTimeRunnable);
			if(mBackToast != null){
				mBackToast.cancel();
			}
			mActivity.finish();
			return true;
		} else {
			isOnKeyBacking = true;
			mBackToast.show();
			mHandler.postDelayed(onBackTimeRunnable, TIME_INTERVAL);
			return true;
		}
	}
	
	private Runnable onBackTimeRunnable = new Runnable() {
		
		@Override
		public void run() {
			isOnKeyBacking = false;
			if(mBackToast != null){
				mBackToast.cancel();
			}
		}
	};
}
