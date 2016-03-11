package com.classic.core.interfaces;

/**
 * 规范注册的接口协议
 *
 * @author 续写经典
 * @date 2015/11/4
 */
public interface IRegister {
    /**
     * 注册广播、服务
     */
    void register();

    /**
     * 注销广播、服务
     */
    void unRegister();
}
