package com.classic.simple.fragment;

import android.view.View;
import butterknife.ButterKnife;
import com.classic.core.fragment.BaseFragment;
import com.classic.simple.event.EventUtil;

public abstract class AppBaseFragment extends BaseFragment {

    public boolean configEventBus() {
        return false;
    }

    @Override public void initView(View parentView) {
        ButterKnife.bind(this, parentView);
    }

    @Override public void register() {
        if (configEventBus()) {
            EventUtil.registerEventBus(this);
        }
    }

    @Override public void unRegister() {
        ButterKnife.unbind(this);
        if (configEventBus()) {
            EventUtil.unRegisterEventBus(this);
        }
    }
}
