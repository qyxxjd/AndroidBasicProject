package com.classic.simple.fragment;

import com.classic.core.log.Logger;
import com.classic.simple.R;

public class Image2Fragment extends AppBaseFragment {

  @Override public int getLayoutResId() {
    return R.layout.activity_listview_item2;
  }

  @Override public void onFirst() {
    Logger.d("亲！只有第一次才会执行哦！");
    //这里可以做一些界面功能引导
  }

  @Override public void onChange() {
    //ToastUtil.showToast(getActivity(),"onChange:"+this.getClass().getSimpleName());
    Logger.d("Image2Fragment --> onChange");
  }

  @Override public void onHidden() {
    super.onHidden();
    Logger.d("Image2Fragment --> onHidden");
  }
}
