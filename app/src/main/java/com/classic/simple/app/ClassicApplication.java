package com.classic.simple.app;

import android.app.Application;
import com.classic.core.exception.CrashHandler;
import com.classic.core.utils.SDcardUtil;

public class ClassicApplication extends Application {
  private static final String ROOT_DIR_NAME = "classic";
  @Override public void onCreate() {
    super.onCreate();
    //可选配置，默认目录名称：download
    SDcardUtil.setRootDirName(ROOT_DIR_NAME);
    SDcardUtil.initDir();
    //配置异常信息收集
    CrashHandler.getInstance(this);
  }
}
