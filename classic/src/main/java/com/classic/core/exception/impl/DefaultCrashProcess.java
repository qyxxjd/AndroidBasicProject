package com.classic.core.exception.impl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import com.classic.core.interfaces.ICrashProcess;
import com.classic.core.utils.DateUtil;
import com.classic.core.utils.SDcardUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 应用名称: AndroidBasicProject
 * 包 名 称: com.classic.core.exception.impl
 *
 * 文件描述: 默认崩溃日志处理
 * 创 建 人: 刘宾
 * 创建时间: 2016/6/21 14:28
 */
public class DefaultCrashProcess implements ICrashProcess {
    private static final String LOG_NAME_PREFIX = "crash_";
    private static final String LOG_NAME_SUFFIX = ".log";
    private Context mContext;

    public DefaultCrashProcess(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override public void onException(Thread thread, Throwable exception) throws Exception {
        final StringBuilder sb = new StringBuilder(LOG_NAME_PREFIX).append(
                new SimpleDateFormat("yyyyMM").format(new Date(System.currentTimeMillis())))
                                                                   .append(LOG_NAME_SUFFIX);
        final File file = new File(SDcardUtil.getLogDirPath(), sb.toString());
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        // 导出手机信息
        savePhoneInfo(pw);
        // 导出发生异常的时间
        pw.println(DateUtil.formatDate(DateUtil.FORMAT, System.currentTimeMillis()));
        pw.println();
        // 导出异常的调用栈信息
        exception.printStackTrace(pw);
        pw.println();
        pw.close();
    }

    private void savePhoneInfo(PrintWriter pw) throws Exception {
        // 应用的版本名称和版本号
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi =
                pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.println("设备信息：");
        pw.print("App Version Name: ");
        pw.println(pi.versionName);
        pw.print("App Version Code: ");
        pw.println(pi.versionCode);

        // android版本号
        pw.print("SDK: ");
        pw.println(Build.VERSION.SDK_INT);
        pw.print("OS Version: ");
        pw.println(Build.VERSION.RELEASE);

        // 手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

        // 手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);

        // cpu架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
        pw.println("------------------------------------");
        pw.println();
    }
}
