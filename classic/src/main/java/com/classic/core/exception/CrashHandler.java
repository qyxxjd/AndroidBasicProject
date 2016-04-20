package com.classic.core.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import com.classic.core.utils.DateUtil;
import com.classic.core.utils.SDcardUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 异常信息收集
 *
 * @author 续写经典
 * @date 2015/11/3
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String LOG_NAME_PREFIX = "crash_";
    public static final String LOG_NAME_SUFFIX = ".log";

    private Context mContext;
    private static CrashHandler sInstance;

    private CrashHandler(Context context) {
        // 将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context;
    }

    public synchronized static CrashHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CrashHandler(context);
        }
        return sInstance;
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用uncaughtException方法
     * thread为出现未捕获异常的线程，
     * exception为未捕获的异常。
     */
    @Override public void uncaughtException(Thread thread, Throwable exception) {
        // 导出异常信息到SD卡中
        try {
            saveToSDCard(exception);
        } catch (Exception e) {
        } finally {
            System.exit(0);
        }
    }

    private void saveToSDCard(Throwable ex) throws Exception {
        final StringBuilder sb = new StringBuilder(LOG_NAME_PREFIX).append(
            new SimpleDateFormat("yyyyMM").format(new Date(System.currentTimeMillis())))
            .append(LOG_NAME_SUFFIX);
        boolean append = false;
        final File file = new File(SDcardUtil.getLogDirPath(), sb.toString());
        if (!file.exists()) {
            file.createNewFile();
        } else {
            append = true;
        }
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
        if (!append) {
            // 导出手机信息
            savePhoneInfo(pw);
        }
        // 导出发生异常的时间
        pw.println(DateUtil.formatDate(DateUtil.FORMAT, System.currentTimeMillis()));
        pw.println();
        // 导出异常的调用栈信息
        ex.printStackTrace(pw);
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
