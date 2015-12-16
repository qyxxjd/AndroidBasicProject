package com.classic.simple.activity;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.classic.core.adapter.AdapterItem;
import com.classic.core.adapter.CommonRcvAdapter;
import com.classic.core.log.Logger;
import com.classic.core.utils.DoubleClickExitHelper;
import com.classic.core.utils.SDcardUtil;
import com.classic.core.utils.ToastUtil;
import com.classic.simple.R;
import com.classic.simple.model.Demo;
import java.util.List;

/**
 * 通用适配器示例By RecyclerView
 */
public class MainActivity extends AppBaseActivity {
  @Bind(R.id.main_rv) RecyclerView recyclerView;

  private List<Demo> demos;
  private DoubleClickExitHelper doubleClickExitHelper;

  @Override public int getLayoutResId() {
    return R.layout.activity_main;
  }

  @Override public void onFirst() {
    super.onFirst();
    Logger.d("onFirst只有第一次才会执行");
    //这里可以做一些界面功能引导
  }

  @Override public void initToolbar() {
    super.initToolbar();
  }

  /**
   * 方法执行顺序：
   * initData() --> initView() --> register()
   */
  @Override public void initData() {
    super.initData();
    demos = Demo.getDemos();
    Logger.object(demos);
    //双击退出应用工具类使用方法，别忘了重写onKeyDown方法（见底部）
    doubleClickExitHelper = new DoubleClickExitHelper(this);
    //doubleClickExitHelper = new DoubleClickExitHelper(this)
    //.setTimeInterval(3000)
    //.setToastContent("再按一次退出demo");
  }
  /**
   * 方法执行顺序：
   * initData() --> initView() --> register()
   */
  @Override public void initView() {
    super.initView();
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

  @Override public void viewClick(View v) {
  }

  /**
   * 方法执行顺序：
   * initData() --> initView() --> register()
   */
  @Override public void register() {
    super.register();
    //这里可以注册一些广播、服务
  }

  @Override public void unRegister() {
    super.unRegister();
    //注销广播、服务
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

  private void itemClick(Demo demo){
    switch (demo.type){
      case Demo.TYPE_ADAPTER:
        startActivity(MainActivity.this, ListViewActivity.class);
        break;
      case Demo.TYPE_CRASH:
        crashTest();
        break;
      case Demo.TYPE_DOWNLOAD:
        startActivity(MainActivity.this, DownloadActivity.class);
        break;
      case Demo.TYPE_EVENT:
        startActivity(MainActivity.this, EventBusActivity.class);
        break;
      case Demo.TYPE_SPLASH:
        startActivity(MainActivity.this,SplashActivity.class);
        break;
      //case Demo.TYPE_UTILS:
      //  ToastUtil.showToast(getApplicationContext(),"TYPE_UTILS");
      //  break;
      case Demo.TYPE_FRAGMENT:
        startActivity(MainActivity.this,FragmentActivity.class);
        break;
    }
  }
  private void crashTest(){
    ToastUtil.showLongToast(this,"程序即将崩溃，崩溃日志请查看："+ SDcardUtil.getLogDir());
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        throw new NullPointerException("666");
      }
    },3000);
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    return doubleClickExitHelper.onKeyDown(keyCode, event);
  }
}
