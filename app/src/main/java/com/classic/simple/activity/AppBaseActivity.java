package com.classic.simple.activity;

import butterknife.ButterKnife;
import com.classic.core.activity.BaseActivity;
import com.classic.simple.event.EventUtil;

/**
 * @author 续写经典
 * @date 2015/11/7
 */
public abstract class AppBaseActivity extends BaseActivity {

  public boolean configEventBus(){
    return false;
  }

  @Override public void initView() {
    ButterKnife.bind(this);
  }
  @Override public void register() {
    if(configEventBus()){
      EventUtil.registerEventBus(this);
    }
  }
  @Override public void unRegister() {
    ButterKnife.unbind(this);
    if(configEventBus()){
      EventUtil.unRegisterEventBus(this);
    }
  }
}
