package com.classic.simple.activity;

import android.os.Bundle;
import butterknife.ButterKnife;
import com.classic.core.activity.BaseActivity;
import com.classic.simple.event.EventUtil;

public abstract class AppBaseActivity extends BaseActivity {

    public boolean configEventBus() {
        return false;
    }

    @Override public void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
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
