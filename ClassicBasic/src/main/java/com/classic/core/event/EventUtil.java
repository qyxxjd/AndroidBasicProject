package com.classic.core.event;

import org.simple.eventbus.EventBus;

/**
 * AndroidEventBus工具类
 * @author 续写经典
 * @date 2015/11/4
 */
public class EventUtil {
  private EventUtil(){}

  /**
   * 注册成为订阅者
   * @param subscriber Activity/Fragment/Class...等对象
   */
  public static void registerEventBus(Object subscriber) {
    EventBus.getDefault().register(subscriber);
  }

  /**
   * 注销订阅者
   */
  public static void unRegisterEventBus(Object subscriber) {
    EventBus.getDefault().unregister(subscriber);
  }
  /**
   * 发布一个事件
   * @param event 发布事件时传递的参数
   */
  public static void post(Object event) {
    EventBus.getDefault().post(event);
  }

  /**
   * 发布一个事件
   * @param event 发布事件时传递的参数
   * @param tag 事件的tag, 类似于BroadcastReceiver的action
   */
  public static void post(Object event, String tag) {
    EventBus.getDefault().post(event, tag);
  }
}
