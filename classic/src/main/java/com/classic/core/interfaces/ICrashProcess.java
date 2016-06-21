package com.classic.core.interfaces;

/**
 * 崩溃日志接口协议
 *
 * @author 续写经典
 * @date 2016/6/21
 */
public interface ICrashProcess {

    void onException(Thread thread, Throwable exception) throws Exception;
}
