package com.classic.core.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import com.classic.core.log.Logger;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 文件操作工具类
 * @author 续写经典
 * @date 2015/11/3
 */
public final class FileUtil {
  private FileUtil() {
  }

  /**
   * 获取文件夹大小
   *
   * @param file File实例
   * @return long 单位为M
   * @throws Exception
   */
  public static long getFolderSize(java.io.File file) throws Exception {
    Logger.d("获取文件大小 - path:" + file.getPath());
    long size = 0;
    java.io.File[] fileList = file.listFiles();
    for (int i = 0; i < fileList.length; i++) {
      if (fileList[i].isDirectory()) {
        size = size + getFolderSize(fileList[i]);
      } else {
        size = size + fileList[i].length();
      }
    }
    Logger.d("获取文件大小 - size:" + size);
    return size;
  }

  /**
   * 删除指定目录下文件及目录
   *
   * @param filePath 文件路径
   * @param isDeleteFolder 是否删除目录
   * @throws IOException
   */
  public static void deleteFolderFile(String filePath, boolean isDeleteFolder) throws IOException {
    if (!TextUtils.isEmpty(filePath)) {
      File file = new File(filePath);

      if (file.isDirectory()) {// 处理目录
        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
          deleteFolderFile(files[i].getAbsolutePath(), isDeleteFolder);
        }
        if (isDeleteFolder && file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
          file.delete();
        }
      } else {
        file.delete();
      }
    }
  }

  /**
   * 格式化文件大小，以B、K、M、G的格式显示
   * @param size
   * @return
   */
  public static String formetFileSize(long size) {
    Logger.d("格式化文件大小前 - size:" + size);
    DecimalFormat df = new DecimalFormat("#.00");
    String fileSizeString = "";
    if (size < 1) {
      fileSizeString = "";
    } else if (size < SizeUtil.KB_2_BYTE) {
      fileSizeString = df.format((double) size) + "B";
    } else if (size < SizeUtil.MB_2_BYTE) {
      fileSizeString = df.format((double) size / SizeUtil.KB_2_BYTE) + "K";
    } else if (size < SizeUtil.GB_2_BYTE) {
      fileSizeString = df.format((double) size / SizeUtil.MB_2_BYTE) + "M";
    } else {
      fileSizeString = df.format((double) size / SizeUtil.GB_2_BYTE) + "G";
    }
    Logger.d("格式化文件大小后 - size:" + fileSizeString);
    return fileSizeString;
  }

  /**
   * 读文件
   * @param path
   * @return
   */
  public static byte[] readSDFile(String path) {
    try {
      File file = new File(path);
      FileInputStream fis = new FileInputStream(file);
      int length = fis.available();
      byte[] buffer = new byte[length];
      fis.read(buffer);
      fis.close();
      return buffer;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 删除文件
   * @param path
   * @return
   */
  public static boolean deleteFile(String path) {
    File file = new File(path);
    if (file.exists()) {
      return file.delete();
    }
    return true;
  }

  /**
   * 扫描指定路径的文件
   */
  public static void fileScan(Context context, String file) {
    Uri data = Uri.parse("file://" + file);
    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
  }
  /**
   * 扫描指定uri
   */
  public static void fileScan(Context context, Uri uri) {
    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
  }

  /**
   * 读取二进制数据
   *
   * @param filePath 文件路径
   */
  public static byte[] read(String filePath) {
    byte[] data = null;
    try {
      BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
      try {
        data = new byte[in.available()];
        in.read(data);
      } finally {
        in.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }

  /**
   * 文件是否存在
   */
  public static boolean exists(String fileName) {
    return new File(fileName).exists();
  }
  /**
   * 文件是否存在
   */
  public static boolean exists(Context context, String fileName) {
    return new File(context.getFilesDir(), fileName).exists();
  }

  /**
   * 存储文本数据
   *
   * @param context 程序上下文
   * @param fileName 文件名，要在系统内保持唯一
   * @param content 文本内容
   * @return boolean 存储成功的标志
   */
  public static boolean writeFile(Context context, String fileName, String content) {
    boolean success = false;
    FileOutputStream fos = null;
    try {
      fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
      byte[] byteContent = content.getBytes();
      fos.write(byteContent);

      success = true;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      CloseUtil.close(fos);
    }

    return success;
  }

  /**
   * 存储文本数据
   *
   * @param filePath 文件名，要在系统内保持唯一
   * @param content 文本内容
   * @return boolean 存储成功的标志
   */
  public static boolean writeFile(String filePath, String content) {
    boolean success = false;
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(filePath);
      byte[] byteContent = content.getBytes();
      fos.write(byteContent);

      success = true;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      CloseUtil.close(fos);
    }

    return success;
  }

  /**
   * 读取文本数据
   *
   * @param context 程序上下文
   * @param fileName 文件名
   * @return String, 读取到的文本内容，失败返回null
   */
  public static String readFile(Context context, String fileName) {
    if (!exists(context, fileName)) {
      return null;
    }
    FileInputStream fis = null;
    String content = null;
    try {
      fis = context.openFileInput(fileName);
      if (fis != null) {

        byte[] buffer = new byte[1024];
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        while (true) {
          int readLength = fis.read(buffer);
          if (readLength == -1) break;
          arrayOutputStream.write(buffer, 0, readLength);
        }
        fis.close();
        arrayOutputStream.close();
        content = new String(arrayOutputStream.toByteArray());
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
      content = null;
    } finally {
      CloseUtil.close(fis);
    }
    return content;
  }

  /**
   * 读取文本数据
   *
   * @param filePath 文件名
   * @return String, 读取到的文本内容，失败返回null
   */
  public static String readFile(String filePath) {
    if (filePath == null || !new File(filePath).exists()) {
      return null;
    }
    FileInputStream fis = null;
    String content = null;
    try {
      fis = new FileInputStream(filePath);
      if (fis != null) {

        byte[] buffer = new byte[1024];
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        while (true) {
          int readLength = fis.read(buffer);
          if (readLength == -1) break;
          arrayOutputStream.write(buffer, 0, readLength);
        }
        fis.close();
        arrayOutputStream.close();
        content = new String(arrayOutputStream.toByteArray());
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
      content = null;
    } finally {
      CloseUtil.close(fis);
    }
    return content;
  }

  /**
   * 将bitmap存储到SD卡(如果文件已存在，会被替换)
   * @param path
   * @param bitmap
   */
  public static void writeBitmapToSD(String path,Bitmap bitmap){

    FileOutputStream fos = null;
    try {
      File file = new File(path);
      if(file.exists()){
        file.delete();
      }
      File parent = file.getParentFile();
      if(!parent.exists()){
        parent.mkdirs();
        file.createNewFile();
      }
      fos = new FileOutputStream(path);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      CloseUtil.close(fos);
    }
  }

  /**
   * 复制文件
   * @param srcFile 源文件
   * @param dstFile 目标文件
   * @return
   */
  public static boolean copy(String srcFile, String dstFile) {
    FileInputStream fis = null;
    FileOutputStream fos = null;
    try {

      File dst = new File(dstFile);
      if (!dst.getParentFile().exists()) {
        dst.getParentFile().mkdirs();
      }

      fis = new FileInputStream(srcFile);
      fos = new FileOutputStream(dstFile);

      byte[] buffer = new byte[1024];
      int len = 0;

      while ((len = fis.read(buffer)) != -1) {
        fos.write(buffer, 0, len);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      CloseUtil.close(fis);
      CloseUtil.close(fos);
    }
    return true;
  }
}
