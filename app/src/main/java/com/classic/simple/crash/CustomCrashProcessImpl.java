package com.classic.simple.crash;

import com.classic.core.interfaces.ICrashProcess;
import com.classic.core.utils.DateUtil;
import com.classic.core.utils.SDcardUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * 自定义崩溃日志处理
 */
public class CustomCrashProcessImpl implements ICrashProcess {
    private static final String SUFFIX = ".txt";

    @Override public void onException(Thread thread, Throwable exception) throws Exception {

        //TODO 这里替换你的自定义逻辑

        final String logName = DateUtil.formatDate("yyyyMMdd_HHmmss",System.currentTimeMillis()) + SUFFIX;
        final File file = new File(SDcardUtil.getLogDirPath(), logName);
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        pw.println(DateUtil.formatDate(DateUtil.FORMAT, System.currentTimeMillis()));
        pw.println();
        exception.printStackTrace(pw);
        pw.println();
        pw.close();
    }

}
