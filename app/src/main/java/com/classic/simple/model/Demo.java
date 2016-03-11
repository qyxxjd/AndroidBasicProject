package com.classic.simple.model;

import android.graphics.Color;
import com.classic.core.utils.DataUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 续写经典
 * @date 2015/11/7
 */
public class Demo implements Serializable {
    public static final int TYPE_SPLASH = 0x00;
    //public static final int TYPE_DOWNLOAD = 0x01;
    public static final int TYPE_ADAPTER = 0x02;
    public static final int TYPE_EVENT = 0x03;
    public static final int TYPE_CRASH = 0x04;
    //public static final int TYPE_UTILS = 0x05;
    public static final int TYPE_FRAGMENT = 0x06;

    public String title;
    public int bgColor;
    public int type;

    public Demo() {
    }

    public Demo(String title, int bgColor, int type) {
        this.title = title;
        this.bgColor = bgColor;
        this.type = type;
    }

    private static List<Demo> demos;

    public static List<Demo> getDemos() {
        if (DataUtil.isEmpty(demos)) {
            demos = new ArrayList<Demo>();
            //demos.add(new Demo("下载", Color.parseColor("#76ff03"), TYPE_DOWNLOAD));
            //demos.add(new Demo("工具类", Color.parseColor("#76ff03"),TYPE_UTILS));
            demos.add(new Demo("启动页", Color.parseColor("#00bcd4"), TYPE_SPLASH));
            demos.add(new Demo("Fragment", Color.parseColor("#ff4081"), TYPE_FRAGMENT));
            demos.add(new Demo("通用适配器", Color.parseColor("#9c27b0"), TYPE_ADAPTER));
            demos.add(new Demo("异常日志收集", Color.parseColor("#e51c23"), TYPE_CRASH));
            demos.add(new Demo("AndroidEventBus", Color.parseColor("#ff9800"), TYPE_EVENT));
        }
        return demos;
    }
}
