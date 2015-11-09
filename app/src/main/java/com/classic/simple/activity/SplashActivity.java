package com.classic.simple.activity;

import com.classic.core.activity.BaseSplashActivity;
import com.classic.simple.R;
import java.util.List;

/**
 * 启动页示例
 */
public class SplashActivity extends BaseSplashActivity {

  @Override protected void setSplashResources(List<SplashImgResource> resources) {
    /**
     * SplashImgResource参数:
     * mResId - 图片资源的ID。
     * playerTime - 图片资源的播放时间，单位为毫秒。。
     * startAlpha - 图片资源开始时的透明程度。0-255之间。
     * isExpand - 如果为true，则图片会被拉伸至全屏幕大小进行展示，否则按原大小展示。
     */
    resources.add(new SplashImgResource(R.mipmap.splash,3000,50f,true));
    resources.add(new SplashImgResource(R.mipmap.splash1,3000,50f,true));
    resources.add(new SplashImgResource(R.mipmap.splash2,3000,50f,true));
  }

  @Override protected boolean isAutoStartNextActivity() {
    return false;
  }
  @Override protected Class<?> nextActivity() {
    return null;
    //如果isAutoStartNextActivity设置为true,这里需要指定跳转的activity
    //return MainActivity.class;
  }

  @Override protected void runOnBackground() {
    //这里可以执行耗时操作、初始化工作
    //请注意：如果执行了耗时操作，那么启动页会等到耗时操作执行完才会进行跳转
    //try {
    //  Thread.sleep(15 * 1000);
    //} catch (InterruptedException e) {
    //  e.printStackTrace();
    //}
  }
}
