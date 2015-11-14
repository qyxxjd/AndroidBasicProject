package com.classic.core.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.classic.core.activity.BaseActivity;
import com.classic.core.event.EventUtil;
import com.classic.core.interfaces.I_Fragment;
import com.classic.core.interfaces.I_Register;
import com.classic.core.utils.SharedPreferencesUtil;

public abstract class BaseFragment extends Fragment implements I_Fragment,I_Register,View.OnClickListener {
  protected Activity activity;
  protected View parentView;
  private SharedPreferencesUtil spUtil;
  protected abstract boolean configButterKnife();
  protected abstract boolean configEventBus();
  protected abstract View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle);

  @Override public void onFirst(){ }
  @Override public void initData() { }
  @Override public void initView(View parentView) { }
  @Override public void onChange() { }
  @Override public void viewClick(View v) { }
  @Override public void showProgress() { }
  @Override public void hideProgress() { }
  @Override public void register() { }
  @Override public void unRegister() { }
  @Override public void onClick(View v) {
    viewClick(v);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    activity = getActivity();
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
    register();
  }

  @Override public void onDestroyView() {
    unRegister();
    if(configButterKnife()){
      ButterKnife.unbind(this);
    }
    if(configEventBus()){
      EventUtil.unRegisterEventBus(this);
    }
    super.onDestroyView();
  }
}
