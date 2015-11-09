# AndroidBasicProject
AndroidBasicProject是一个免费的、开源的、简易的Android基础项目，方便您快速的进行Android应用程序的开发。包含以下内容：
基础的Activity、Fragment；Event事件管理；下载管理；异常信息收集；日志打印；通用适配器；常用工具类。

##Demo截图
[APK下载](https://github.com/qyxxjd/AndroidBasicProject/blob/master/apk/demo_1.0.apk)

![](https://github.com/qyxxjd/AndroidBasicProject/blob/master/screenshots/Screenshot_2015-11-09-14-26-58.png)
![](https://github.com/qyxxjd/AndroidBasicProject/blob/master/screenshots/Screenshot_2015-11-09-13-26-07.png)

![](https://github.com/qyxxjd/AndroidBasicProject/blob/master/screenshots/Screenshot_2015-11-09-13-27-17.png)
![](https://github.com/qyxxjd/AndroidBasicProject/blob/master/screenshots/Screenshot_2015-11-09-13-40-44.png)

##代码示例
Activity示例
```java
public class TestActivity extends BaseActivity {

  @Bind(R.id.button) Button button;
  @Bind(R.id.text) TextView text;

  //是否使用ButterKnife,如果设置false,需要在initView里面findviewById...
  @Override protected boolean configButterKnife() {
    return true;
  }

  //是否使用AndroidEventBus
  @Override protected boolean configEventBus() {
    return true;
  }

  @Override public int setLayoutResId() {
    return R.layout.activity_test;
  }

  @Override protected void onFirst() {
    //只有第一次会执行此方法
  }

  /**
   * 方法执行顺序：
   * initData() --> initView() --> register()
   */
  @Override public void initData() {
    //可以初始化一些数据
  }
  @Override public void initView() {
    //初始化view
    button.setOnClickListener(this);
    text.setOnClickListener(this);
  }

  @Override public void viewClick(View v) {
    //处理一些点击事件
    switch (v.getId()){
      case R.id.button:

        break;
      case R.id.text:

        break;
    }
  }

  @Override public void register() {
    //这里可以注册一些广播、服务
  }

  @Override public void unRegister() {
    //注销广播、服务
  }

  @Override public void showProgress() {
    //需要显示进度条，可以重写此方法
  }

  @Override public void hideProgress() {
    //关闭进度条
  }
}
```

启动页示例
```java
public class SplashActivity extends BaseSplashActivity {

  @Override protected void setSplashResources(List<SplashImgResource> resources) {
    /**
     * SplashImgResource参数:
     * mResId - 图片资源的ID。
     * playerTime - 图片资源的播放时间，单位为毫秒。。
     * startAlpha - 图片资源开始时的透明程度。0-255之间。
     * isExpand - 如果为true，则图片会被拉伸至全屏幕大小进行展示，否则按原大小展示。
     */
    resources.add(new SplashImgResource(R.mipmap.splash,1500,100f,true));
    resources.add(new SplashImgResource(R.mipmap.splash1,1500,100f,true));
    resources.add(new SplashImgResource(R.mipmap.splash2,1500,100f,true));
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
```

AndroidEventBus示例  [点击查看更多介绍](https://github.com/bboyfeiyu/AndroidEventBus)
```java
  //发布一个事件
  EventUtil.post(params,EVENT_TAG);

  /**
   * 接收事件
   * 当用户post事件时,只有指定了EVENT_TAG的事件才会触发该函数,
   * ThreadMode.MAIN : 默认方法，执行在UI线程，可省略不写
   * ThreadMode.ASYNC: 执行在一个独立的线程
   * ThreadMode.POST : post函数在哪个线程执行,该函数就执行在哪个线程
   */
  @Subscriber(tag = EVENT_TAG,mode = ThreadMode.MAIN)
  public void updateUI(Object params){
    //业务逻辑处理
  }
```

通用适配器示例  [点击查看更多介绍](https://github.com/tianzhijiexian/Android-Best-Practices)
```java
  @Override public void initView() {
    LinearLayoutManager manager = new LinearLayoutManager(this);
    manager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(manager);
    //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
    recyclerView.setHasFixedSize(true);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(new CommonRcvAdapter<Demo>(demos) {
      @NonNull @Override public AdapterItem<Demo> getItemView(Object o) {
        return new TextItem();
      }
    });
  }
  private class TextItem implements AdapterItem<Demo> {

    @Override public int getLayoutResId() {
      return R.layout.activity_main_item;
    }
    private ViewHolder holder;
    private View view;
    @Override public void onBindViews(View view) {
      this.view = view;
      //在这里做findviewById的工作吧
      holder = new ViewHolder(view);
    }

    @Override public void onSetViews() {
      //这个方法仅仅在item构建时才会触发，所以在这里也仅仅建立一次监听器，不会重复建立
      holder.mainItemCardview.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          final int position = recyclerView.getChildAdapterPosition(v);
          itemClick(demos.get(position));
        }
      });
    }

    @Override public void onUpdateViews(Demo demo, int i) {
      // 在每次适配器getView的时候就会触发，这里避免做耗时的操作
      holder.mainItemCardview.setCardBackgroundColor(demo.bgColor);
      holder.mainItemTv.setText(demo.title);
    }

  }
  class ViewHolder {
    @Bind(R.id.main_item_tv) TextView mainItemTv;
    @Bind(R.id.main_item_cardview) CardView mainItemCardview;

    ViewHolder(View view) {
      ButterKnife.bind(this, view);
    }
  }
```






