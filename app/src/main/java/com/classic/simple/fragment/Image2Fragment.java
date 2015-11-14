package com.classic.simple.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.classic.core.fragment.BaseFragment;
import com.classic.core.log.Logger;
import com.classic.core.utils.ToastUtil;
import com.classic.simple.R;

public class Image2Fragment extends BaseFragment {
  @Override protected boolean configButterKnife() {
    return true;
  }

  @Override protected boolean configEventBus() {
    return false;
  }

  @Override public void onFirst() {
    Logger.d("亲！只有第一次才会执行哦！");
    //这里可以做一些界面功能引导
  }

  @Override public void onChange() {
    ToastUtil.showToast(getActivity(),"onChange:"+this.getClass().getSimpleName());
  }

  @Override
  protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
    return inflater.inflate(R.layout.activity_listview_item2,container,false);
  }

  /**
   * 方法执行顺序：
   * initData() --> initView(view)
   */
  @Override public void initData() {
    //初始化数据
  }
  /**
   * 方法执行顺序：
   * initData() --> initView(view)
   */
  @Override public void initView(View parentView) {
    //初始化view
  }
}
