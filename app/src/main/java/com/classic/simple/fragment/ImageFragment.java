package com.classic.simple.fragment;

import com.classic.core.log.Logger;
import com.classic.simple.R;

public class ImageFragment extends AppBaseFragment {

    @Override public int getLayoutResId() {
        return R.layout.activity_listview_item;
    }

    @Override public void onFirst() {
        Logger.d("亲！只有第一次才会执行哦！");
        //这里可以做一些界面功能引导
    }

    @Override public void onChange() {
        //ToastUtil.showToast(getActivity(),"onChange:"+this.getClass().getSimpleName());
        Logger.d("ImageFragment --> onChange");
    }

    @Override public void onHidden() {
        super.onHidden();
        Logger.d("ImageFragment --> onHidden");
    }
}
