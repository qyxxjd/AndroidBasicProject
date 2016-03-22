package com.classic.simple.app;

import android.app.Application;
import com.classic.core.BasicConfig;

public class ClassicApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();

        /**
         * 默认配置，适用于正式版本
         * 内部调用了: initDir() initLog(false) initExceptionHandler()三个方法
         */
        //BasicConfig.getInstance(this).init();

        /**
         * 自定义配置
         * initDir() 初始化SDCard缓存目录
         * initLog() 初始化日志打印
         * initExceptionHandler() 初始化异常信息收集
         */
        BasicConfig.getInstance(this)
                   .initDir() // or initDir(rootDirName)
                   //.initExceptionHandler()
                   .initLog(true);
    }
}
