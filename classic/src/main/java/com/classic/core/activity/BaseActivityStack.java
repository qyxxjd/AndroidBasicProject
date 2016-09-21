package com.classic.core.activity;

import android.app.Activity;
import android.content.Context;
import com.classic.core.interfaces.IActivity;
import java.util.Stack;

/**
 * Activity栈管理
 *
 * @author 续写经典
 * @version 1.0 2015/11/4
 */
public final class BaseActivityStack {
    private BaseActivityStack() { }

    private static Stack<IActivity> sActivityStack;
    private static final BaseActivityStack INSTANCE = new BaseActivityStack();

    public static BaseActivityStack getInstance() {
        return INSTANCE;
    }


    /**
     * 获取当前Activity栈中元素个数
     */
    public int getCount() {
        return sActivityStack.size();
    }


    /**
     * 添加Activity到栈
     */
    public void addActivity(IActivity activity) {
        if (sActivityStack == null) {
            sActivityStack = new Stack<>();
        }
        sActivityStack.add(activity);
    }


    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity topActivity() {
        if (sActivityStack == null) {
            throw new NullPointerException("Activity stack is Null");
        }
        if (sActivityStack.isEmpty()) {
            return null;
        }
        IActivity activity = sActivityStack.lastElement();
        return (Activity) activity;
    }


    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        IActivity activity = null;
        for (IActivity aty : sActivityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return null == activity ? null : (Activity) activity;
    }


    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        IActivity activity = sActivityStack.lastElement();
        finishActivity((Activity) activity);
    }


    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            sActivityStack.remove(activity);
            activity.finish();
        }
    }


    /**
     * 结束指定的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (IActivity activity : sActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity((Activity) activity);
            }
        }
    }


    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     */
    public void finishOthersActivity(Class<?> cls) {
        for (IActivity activity : sActivityStack) {
            if (!(activity.getClass().equals(cls))) {
                finishActivity((Activity) activity);
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = sActivityStack.size(); i < size; i++) {
            if (null != sActivityStack.get(i)) {
                ((Activity) sActivityStack.get(i)).finish();
            }
        }
        sActivityStack.clear();
    }


    /**
     * 应用程序退出
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Runtime.getRuntime().exit(-1);
        }
    }
}
