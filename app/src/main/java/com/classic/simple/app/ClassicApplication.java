package com.classic.simple.app;

import android.app.Application;
import com.classic.core.exception.CrashHandler;
import com.classic.core.log.Logger;
import com.classic.core.utils.SDcardUtil;

public class ClassicApplication extends Application {
    public static final String LOG_TAG = "classic";
    public static final String ROOT_DIR_NAME = "classic";

    @Override public void onCreate() {
        super.onCreate();

        //日志打印配置
        Logger.init(LOG_TAG)                   // default PRETTYLOGGER
            .hideThreadInfo()                // default show
        //.logLevel(LogLevel.NONE)       // default LogLevel.FULL
        //.methodOffset(2)               // default 0
        //.logTool(new AndroidLogTool()) // custom log tool, optional
        ;
        //可选配置，默认目录名称：download
        SDcardUtil.setRootDirName(ROOT_DIR_NAME);
        SDcardUtil.initDir();
        //配置异常信息收集
        CrashHandler.getInstance(this);
    }
}
