package com.classic.simple.activity;

import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import butterknife.Bind;
import com.classic.core.activity.BaseActivity;
import com.classic.simple.R;
import java.util.ArrayList;
import java.util.List;
import kale.adapter.AdapterItem;
import kale.adapter.abs.CommonAdapter;

/**
 * 通用适配器示例By ListView
 */
public class ListViewActivity extends BaseActivity {
  @Bind(R.id.listview_lv)
  ListView listView;
  private List<Integer> typeList;
  //是否启用ButterKnife,如果设置false,需要在initView里面findviewById...
  @Override protected boolean configButterKnife() {
    return true;
  }
  //是否启用AndroidEventBus
  @Override protected boolean configEventBus() {
    return false;
  }

  @Override public int setLayoutResId() {
    return R.layout.activity_listview;
  }

  /**
   * 方法执行顺序：
   * initData() --> initView() --> register()
   */
  @Override public void initData() {
    typeList = new ArrayList<Integer>();
    for(int i = 0;i<20;i++){
      typeList.add(i%3==0?1:0);
    }
  }
  /**
   * 方法执行顺序：
   * initData() --> initView() --> register()
   */
  @Override public void initView() {
    getSupportActionBar().setTitle("通用适配器示例");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    /*
     * ListView和RecyclerView唯一的不同就是：
     * ListView用的CommonAdapter；
     * RecyclerView用的CommonRcvAdapter；
     * 其它的使用完全一样。
     */
    listView.setAdapter(new CommonAdapter<Integer>(typeList,2) {
      @Override public Object getItemViewType(Integer type) {
        return type;
      }

      @NonNull @Override public AdapterItem<Integer> getItemView(Object type) {
        switch (Integer.valueOf(type.toString())) {
          case 1:
            return new ImageItem2();
          default:
            return new ImageItem();
        }
      }
    });
  }

  private class ImageItem implements AdapterItem<Integer> {

    @Override public int getLayoutResId() {
      return R.layout.activity_listview_item;
    }
    @Override public void onBindViews(View view) {
      //在这里做findviewById的工作吧
    }

    @Override public void onSetViews() {
      //这个方法仅仅在item构建时才会触发，所以在这里也仅仅建立一次监听器，不会重复建立
    }

    @Override public void onUpdateViews(Integer type, int i) {
      // 在每次适配器getView的时候就会触发，这里避免做耗时的操作
    }
  }
  private class ImageItem2 implements AdapterItem<Integer> {

    @Override public int getLayoutResId() {
      return R.layout.activity_listview_item2;
    }
    @Override public void onBindViews(View view) {
    }

    @Override public void onSetViews() {
    }

    @Override public void onUpdateViews(Integer type, int i) {
    }
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    if(item.getItemId() == android.R.id.home)
    {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
