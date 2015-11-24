# AndroidBasicProject
AndroidBasicProject是一个免费的、开源的、简易的Android基础项目，方便您快速的进行Android应用程序的开发。包含以下内容：
基础的Activity、Fragment；Event事件管理；下载管理；异常信息收集；日志打印；通用适配器；常用工具类。

本项目并没有集成：图片加载、网络请求、数据库，不同的项目不同的需求可能会有不同的选择。有以下优秀的库可供参考：

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
    compile 'com.classic.core:classic:1.3'
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
> v1.2
>
> 修复部分库冲突问题；
>
> 修复一些bug。
>
> v1.3
>
> 规范Fragment接口；
>
> BaseActivity添加initPre()、initToolbar()方法。

##感谢
[ButterKnife - JakeWharton](https://github.com/JakeWharton/butterknife)

[AndroidEventBus - Mr.SIMPLE](https://github.com/bboyfeiyu/AndroidEventBus)

[CommonAdapter - tianzhijiexian](https://github.com/tianzhijiexian/CommonAdapter)

[logger - Orhan Obut](https://github.com/orhanobut/logger)

[LogUtils - pengwei1024](https://github.com/pengwei1024/LogUtils)

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
   * initPre() --> initData() --> initView() --> register()
   */
  @Override public void initPre() {
    //这个方法会在setContentView(...)方法之前执行
  }
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
Fragment示例
```java
public class TestFragment extends BaseFragment {
  @Override protected boolean configButterKnife() {
    return true;
  }
  @Override protected boolean configEventBus() {
    return true;
  }

  @Override
  protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
    return inflater.inflate(R.layout.fragment_test, container, false);
  }
  @Override public void onFirst() {
    //只有第一次会执行此方法
  }
  @Override public void onChange() {
    //这个方法会在Fragment从后台切换到前台时执行
  }
  /**
   * 方法执行顺序：
   * initData() --> initView() --> register()
   */
  @Override public void initData() {
    //可以初始化一些数据
  }
  @Override public void initView(View parentView) {
    //初始化view
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

AndroidEventBus示例 [点击查看更多介绍](https://github.com/bboyfeiyu/AndroidEventBus)
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



