package com.classic.core;

import android.content.Context;
import android.support.annotation.NonNull;

import com.classic.core.exception.CrashHandler;
import com.classic.core.log.LogLevel;
import com.classic.core.log.Logger;
import com.classic.core.utils.SDcardUtil;

/**
 * 全局配置
 *
 * @author 续写经典
 * @date 2016/03/13
 */
public final class BasicConfig {

    private Context context;
    private BasicConfig(Context context){ this.context = context; }
    private static BasicConfig config ;

    public static final synchronized BasicConfig getInstance(@NonNull Context context){
        if(null == config){
            synchronized (BasicConfig.class){
                if(null == config){
                    config = new BasicConfig(context);
                }
            }
        }
        return config;
    }

    /**
     * 默认配置
     * @return
     */
    public void init(){
        initDir();
        initLog();
        initExceptionHandler();
    }

    /**
     * 初始化SDCard缓存目录
     * <pre>默认根目录名称：当前包名</pre>
     * @return
     */
    public BasicConfig initDir(){
        initDir(context.getPackageName());
        return this;
    }

    /**
     * 初始化SDCard缓存目录
     * @param dirName 根目录名称
     * @return
     */
    public BasicConfig initDir(@NonNull String dirName){
        SDcardUtil.setRootDirName(dirName);
        SDcardUtil.initDir();
        return this;
    }

    /**
     * 初始化异常信息收集
     * @return
     */
    public BasicConfig initExceptionHandler(){
        CrashHandler.getInstance(context);
        return this;
    }

    /**
     * 初始化日志打印
     * @return
     */
    public BasicConfig initLog(){
        Logger.init().logLevel(LogLevel.FULL) ;
        return this;
    }

    /**
     * 初始化日志打印
     * @param tag 日志标示
     * @return
     */
    public BasicConfig initLog(@NonNull String tag){
        Logger.init(tag).logLevel(LogLevel.FULL);
        return this;
    }
}
