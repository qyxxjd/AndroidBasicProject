AndroidBasicProject是一个简易的Android基础项目，方便您快速的进行Android应用程序的开发。包含以下内容：
基础的Activity、Fragment；下载管理；异常信息收集；日志打印；通用适配器；常用工具类。

配合以下库使用，效果更酸爽，哈哈！

图片加载  [glide](https://github.com/bumptech/glide)
  [fresco](https://github.com/facebook/fresco)
  [picasso](https://github.com/square/picasso)

网络请求  [okhttp](https://github.com/square/okhttp)
  [retrofit](https://github.com/square/retrofit)
  [android-async-http](https://github.com/loopj/android-async-http)

数据库  [greenDAO](https://github.com/greenrobot/greenDAO)
  [ormlite](https://github.com/j256/ormlite-android)
  [ActiveAndroid](https://github.com/pardom/ActiveAndroid)

##Demo截图
[APK下载](https://github.com/qyxxjd/AndroidBasicProject/blob/master/apk/demo-1.2.apk?raw=true)

![](https://github.com/qyxxjd/AndroidBasicProject/blob/master/screenshots/Screenshot_2015-11-09-14-26-58.png)
![](https://github.com/qyxxjd/AndroidBasicProject/blob/master/screenshots/Screenshot_2015-11-09-13-26-07.png)

![](https://github.com/qyxxjd/AndroidBasicProject/blob/master/screenshots/Screenshot_2015-11-09-13-27-17.png)
![](https://github.com/qyxxjd/AndroidBasicProject/blob/master/screenshots/Screenshot_2015-11-09-13-40-44.png)

##使用步骤
第一步：
```gradle
dependencies {
    compile 'com.classic.core:classic:1.4'
}
```
第二步：
```java
public class YourApplication extends Application {
  public static final String LOG_TAG = "classic";
  private static final String ROOT_DIR_NAME = "classic";

  @Override public void onCreate() {
    super.onCreate();

    //可选配置，默认目录名称：download
    SDcardUtil.setRootDirName(ROOT_DIR_NAME);
    SDcardUtil.initDir();
    //配置异常信息收集
    CrashHandler.getInstance(this);
    //日志打印配置
    Logger
        .init(LOG_TAG)                   // default PRETTYLOGGER
        .hideThreadInfo()                // default show
        //.logLevel(LogLevel.NONE)       // default LogLevel.FULL
        //.methodOffset(2)               // default 0
        //.logTool(new AndroidLogTool()) // custom log tool, optional
        ;
  }
}
```

##更新日志
```java
v1.4

BaseActivity、BaseFragment添加initInstanceState方法，方便做一些状态的恢复操作。
BaseFragment添加onHidden方法，当前fragment被切换到后台时会执行此方法。
修复MoneyUtil的一些bug，支持链式计算：
MoneyUtil.obtain(13.89)
        .add(56.73)
        .multiply(99.789)
        .divide(16)
        .multiply(7)
        .subtract(66.123)
        .create()。
```

##感谢
[CommonAdapter - tianzhijiexian](https://github.com/tianzhijiexian/CommonAdapter)

[logger - Orhan Obut](https://github.com/orhanobut/logger)

[LogUtils - pengwei1024](https://github.com/pengwei1024/LogUtils)

##代码示例
Activity示例
```java
public class TestActivity extends BaseActivity {

  @Bind(R.id.main_rv) RecyclerView recyclerView;

  private List<Demo> demos;
  private DoubleClickExitHelper doubleClickExitHelper;

  @Override public int getLayoutResId() {
    return R.layout.activity_test;
  }

  @Override public void onFirst() {
    super.onFirst();
    Logger.d("onFirst只有第一次才会执行");
    //这里可以做一些界面功能引导
  }

  /**
   * 方法执行顺序：
   * initPre() --> initInstanceState(Bundle savedInstanceState) -->
   * initData() --> initView() --> register()
   */
  @Override public void initPre() {
    super.initPre();
    //这个方法会在setContentView(...)方法之前执行
  }

  @Override public void initInstanceState(Bundle savedInstanceState) {
    super.initInstanceState(savedInstanceState);
    //这里可以做一些状态的恢复操作
  }

  @Override public void initData() {
    super.initData();
    demos = Demo.getDemos();
    Logger.object(demos);
    //双击退出应用工具类使用方法，别忘了重写onKeyDown方法（见底部）
    doubleClickExitHelper = new DoubleClickExitHelper(this);
    //.setTimeInterval(3000)
    //.setToastContent("再按一次退出demo");
  }

  @Override public void initView() {
    super.initView();
    ButterKnife.bind(this);

    recyclerView.setOnClickListener(this);
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

  @Override public void register() {
    super.register();
    EventUtil.registerEventBus(this);
    //这里可以注册一些广播、服务
  }

  @Override public void unRegister() {
    super.unRegister();
    ButterKnife.unbind(this);
    EventUtil.unRegisterEventBus(this);
    //注销广播、服务
  }

  @Override public void viewClick(View v) {
    switch (v.getId()){
      case R.id.main_rv:
        //点击事件处理
        break;
    }
  }
  @Override public void showProgress() {
    //需要显示进度条，可以重写此方法
  }

  @Override public void hideProgress() {
    //关闭进度条
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    return doubleClickExitHelper.onKeyDown(keyCode, event);
  }
}
```
Fragment示例
```java
public class TestFragment extends BaseFragment {

  @Override public int getLayoutResId() {
    return R.layout.fragment_test;
  }

  @Override public void onChange() {
    //当前fragment从后台被切换到前台时会执行此方法
  }

  @Override public void onHidden() {
    super.onHidden();
    //当前fragment从前台被切换到后台时会执行此方法
  }

  @Override public void onFirst() {
    super.onFirst();
    Logger.d("onFirst只有第一次才会执行");
    //这里可以做一些界面功能引导
  }

  /**
   * 方法执行顺序：
   * initInstanceState(Bundle savedInstanceState) -->
   * initData() --> initView(View parentView) --> register()
   */
  @Override public void initInstanceState(Bundle savedInstanceState) {
    super.initInstanceState(savedInstanceState);
    //这里可以做一些状态的恢复操作
  }

  @Override public void initData() {
    super.initData();
  }

  @Override public void initView(View parentView) {
    super.initView(parentView);
    ButterKnife.bind(this, parentView);
  }

  @Override public void register() {
    super.register();
    //这里可以注册一些广播、服务
  }

  @Override public void unRegister() {
    super.unRegister();
    ButterKnife.unbind(this);
    //注销广播、服务
  }

  @Override public void viewClick(View v) {
    //处理一些点击事件
    switch (v.getId()){
      case R.id.button:
        //...
        break;
      case R.id.text:
        //...
        break;
    }
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

通用适配器示例 [点击查看更多介绍](https://github.com/tianzhijiexian/CommonAdapter)
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
打印日志 [点击查看更多介绍](https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.8/log/log.md)
```java
Logger.d("hello");
Logger.e("hello");
Logger.w("hello");
Logger.v("hello");
Logger.wtf("hello");
//打印json数据
Logger.json(JSON_CONTENT);
//打印xml数据
Logger.xml(XML_CONTENT);
//打印对象(Bean,Array,Collection,Map...)
Logger.object(object);
```
注意事项：确保包装选项是禁用的
![](https://github.com/qyxxjd/AndroidBasicProject/blob/master/screenshots/log.png)

##工具类
* [AppInfoUtil - 应用程序相关信息](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/AppInfoUtil.java) <br/>
* [BitmapUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/BitmapUtil.java) <br/>
* [CloseUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/CloseUtil.java)<br/>
* [ConversionUtil - 单位转换](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/ConversionUtil.java)<br/>
* [DataUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/DataUtil.java)<br/>
* [DateUtil - 日期操作](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/DateUtil.java)<br/>
* [DeviceUtil - 设备信息](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/DeviceUtil.java)<br/>
* [DoubleClickExitHelper - 双击退出应用程序](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/DoubleClickExitHelper.java)<br/>
* [EditTextUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/EditTextUtil.java)<br/>
* [FileUtil - 文件操作](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/FileUtil.java)<br/>
* [HtmlUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/HtmlUtil.java)<br/>
* [IntentUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/IntentUtil.java)<br/>
* [IpUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/IpUtil.java)<br/>
* [KeyBoardUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/KeyBoardUtil.java)<br/>
* [MatcherUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/MatcherUtil.java)<br/>
* [MoneyUtil - 高精度数据计算](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/MoneyUtil.java)<br/>
* [NetworkUtil - 网络状态](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/NetworkUtil.java)<br/>
* [PackageUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/PackageUtil.java)<br/>
* [ResourceUtil - 资源文件操作](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/ResourceUtil.java)<br/>
* [SDcardUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/SDcardUtil.java)<br/>
* [SharedPreferencesUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/SharedPreferencesUtil.java)<br/>
* [StringUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/StringUtil.java)<br/>
* [ToastUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/ToastUtil.java)<br/>
* [ViewHolder](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/ViewHolder.java)<br/>
* [WifiUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/WifiUtil.java)<br/>
* [WindowUtil](https://github.com/qyxxjd/AndroidBasicProject/blob/master/classic/src/main/java/com/classic/core/utils/WindowUtil.java)<br/>

##关于
* Blog: [http://blog.csdn.net/qy1387](http://blog.csdn.net/qy1387)
* Email: [pgliubin@gmail.com](http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=pgliubin@gmail.com)

##License
```
Copyright 2015 classic

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```



