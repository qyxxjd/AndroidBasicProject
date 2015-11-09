package com.classic.core.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.classic.core.activity.BaseActivity;
import com.classic.core.event.EventUtil;
import com.classic.core.utils.SharedPreferencesUtil;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
  protected View parentView;
  private SharedPreferencesUtil spUtil;
  protected abstract boolean configButterKnife();
  protected abstract boolean configEventBus();
  protected abstract View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle);

  /** 第一次启动会执行此方法 */
  protected void onFirst(){ }
  protected void initView(View parentView) { }

  protected void initData() { }

  public void onChange() { }

  protected void viewClick(View v) { }

  @Override public void onClick(View v) {
    viewClick(v);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    parentView = inflaterView(inflater, container, savedInstanceState);
    if(configButterKnife()){
      ButterKnife.bind(this, parentView);
    }
    if(configEventBus()){
      EventUtil.registerEventBus(this);
    }
    spUtil = new SharedPreferencesUtil(getActivity(), BaseActivity.SP_NAME);
    final String simpleName = this.getClass().getSimpleName();
    if(spUtil.getBooleanValue(simpleName, true)){
      onFirst();
      spUtil.putBooleanValue(simpleName, false);
    }
    initData();
    return parentView;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView(parentView);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if(configButterKnife()){
      ButterKnife.unbind(this);
    }
    if(configEventBus()){
      EventUtil.unRegisterEventBus(this);
    }
  }
}
