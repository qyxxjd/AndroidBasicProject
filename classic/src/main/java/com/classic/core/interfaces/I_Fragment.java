package com.classic.core.interfaces;

import android.view.View;

/**
 * 规范Fragment接口协议
 * @author 续写经典
 * @date 2015/11/12
 */
public interface I_Fragment {

  /**
   * Fragment被切换到前台时调用
   */
  void onChange();

  /** 第一次启动会执行此方法 */
  void onFirst();
  /**
   * 初始化数据
   */
  void initData();

  /**
   * 初始化控件
   */
  void initView(View parentView);

  /**
   * 点击事件回调方法
   */
  void viewClick(View v);

  /**
   * 显示进度条
   */
  void showProgress();

  /**
   * 隐藏进度条
   */
  void hideProgress();

}
