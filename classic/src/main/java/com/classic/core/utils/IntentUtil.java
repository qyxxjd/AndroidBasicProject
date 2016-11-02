package com.classic.core.utils;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import com.orhanobut.logger.Logger;
import java.io.File;

/**
 * 跳转到对应的系统界面
 *
 * @author 续写经典
 * @version 1.0 2015/11/3
 */
public final class IntentUtil {
    private IntentUtil() {}

    /** 进入拨号界面 */
    public static void dial(@NonNull Context context, @NonNull String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 直接拨号
     * 需要权限：android.permission.CALL_PHONE
     */
    public static void call(@NonNull Context context, @NonNull String phoneNumber) {
        Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) ==
            PackageManager.PERMISSION_GRANTED) {
            context.startActivity(intentPhone);
        } else {
            Logger.e("no permission: android.permission.CALL_PHONE");
        }
    }

    /** 用浏览器打开url */
    public static void browser(@NonNull Context context, @NonNull String url) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    /** 用系统浏览器打开url */
    public static void browserBySystem(@NonNull Context context, @NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        context.startActivity(intent);
    }

    /** 发送短信 */
    public static void sendSms(@NonNull Context context, @NonNull String smsBody) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        context.startActivity(intent);
    }

    /** 发送短信 */
    public static void sendSms(@NonNull Context context, @NonNull String phone, @NonNull String smsBody) {
        Uri smsToUri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        context.startActivity(intent);
    }

    /** 跳转到设置界面 */
    public static void setting(@NonNull Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    /** 跳转到网络设置界面 */
    public static void settingNetwork(@NonNull Context context) {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            intent.setClassName("com.android.settings",
                                "com.android.settings.WirelessSettings");
        }
        context.startActivity(intent);
    }

    /**
     * 跳转到系统程序详细信息界面
     */
    public static void startInstalledAppDetails(@NonNull Context context, @NonNull String packageName) {
        Intent intent = new Intent();
        int sdkVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", packageName, null));
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra((sdkVersion == Build.VERSION_CODES.FROYO
                             ? "pkg"
                             : "com.android.settings.ApplicationPkgName"), packageName);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 分享图片
     *
     * @param title 标题
     * @param imageUri 图片uri
     */
    public static void shareImage(@NonNull Context context, @NonNull String title, @NonNull Uri imageUri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(shareIntent, title));
    }

    /**
     * 分享文本
     *
     * @param titie 标题
     * @param subject 主题
     * @param content 内容
     */
    public static void shareText(@NonNull Context context,
                                 @NonNull String titie,
                                 @NonNull String subject,
                                 @NonNull String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, titie));
    }


    /**
     * 安装应用
     * @param context
     * @param apkPath
     * @param authority Android 7.0以上用到的参数
     * @see {https://developer.android.com/reference/android/support/v4/content/FileProvider.html}
     * @see {http://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed}
     */
    public static void installApp(@NonNull Context context, @NonNull String apkPath, String authority) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = null;
        File file = new File(apkPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 查看图片
     * @param context
     * @param file
     * @param authority Android 7.0以上用到的参数
     * @see {https://developer.android.com/reference/android/support/v4/content/FileProvider.html}
     * @see {http://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed}
     */
    public static void viewPicture(@NonNull Context context, @NonNull File file, String authority) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
