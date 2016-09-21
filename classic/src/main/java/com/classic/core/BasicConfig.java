package com.classic.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.classic.core.exception.CrashHandler;
import com.classic.core.exception.impl.DefaultCrashProcess;
import com.classic.core.interfaces.ICrashProcess;
import com.classic.core.utils.SDcardUtil;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;

/**
 * 全局配置
 *
 * @author 续写经典
 * @version 2.2 2016/03/13
 */
public final class BasicConfig {
    private static final String LOG_TAG = "classic";

    private Context mContext;
    private BasicConfig(Context context){ this.mContext = context; }
    private volatile static BasicConfig sBasicConfig;

    public static final BasicConfig getInstance(@NonNull Context context){
        if(null == sBasicConfig){
            synchronized (BasicConfig.class){
                if(null == sBasicConfig){
                    sBasicConfig = new BasicConfig(context.getApplicationContext());
                }
            }
        }
        return sBasicConfig;
    }

    /**
     * 默认配置
     */
    public void init(){
        initDir();
        initLog(false);
        initExceptionHandler();
    }

    /**
     * 初始化SDCard缓存目录
     * <pre>默认根目录名称：当前包名</pre>
     * @return
     */
    public BasicConfig initDir(){
        initDir(mContext.getPackageName());
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
     * 默认异常信息处理
     * @return
     */
    public BasicConfig initExceptionHandler(){
        return initExceptionHandler(new DefaultCrashProcess(mContext));
    }
    /**
     * 自定义异常信息处理
     * @return
     */
    public BasicConfig initExceptionHandler(ICrashProcess crashProcess){
        CrashHandler.getInstance(crashProcess);
        return this;
    }

    /**
     * 日志打印参数配置
     * @param isDebug true:打印全部日志，false:不打印日志
     * @return
     */
    public BasicConfig initLog(boolean isDebug){
        initLog(LOG_TAG, null, true, null,isDebug);
        return this;
    }

    /**
     * 日志打印参数配置
     * @param tag 日志标示
     * @return
     */
    public BasicConfig initLog(String tag){
        initLog(tag,true);
        return this;
    }
    /**
     * 日志打印参数配置
     * @param tag 日志标示，可以为空
     * @param isDebug true:打印全部日志，false:不打印日志
     * @return
     */
    public BasicConfig initLog(String tag,boolean isDebug){
        initLog(tag,null,true,null,isDebug);
        return this;
    }

    /**
     * 日志打印参数配置
     * @param tag 日志标示，可以为空
     * @param methodCount 显示方法行数，默认为：2
     * @param isHideThreadInfo 是否显示线程信息，默认显示
     * @param adapter 自定义log输出
     * @param isDebug true:打印全部日志，false:不打印日志
     * @return
     */
    public BasicConfig initLog(String tag,Integer methodCount,
                               boolean isHideThreadInfo, LogAdapter adapter,
                               boolean isDebug){

        Settings settings = Logger.init(TextUtils.isEmpty(tag) ? LOG_TAG : tag);
        if(null != methodCount){
            settings.methodCount(methodCount);
        }
        if(isHideThreadInfo){
            settings.hideThreadInfo();
        }
        if(null != adapter){
            settings.logAdapter(adapter);
        }
        settings.logLevel(isDebug ? LogLevel.FULL : LogLevel.NONE);
        return this;
    }
}
