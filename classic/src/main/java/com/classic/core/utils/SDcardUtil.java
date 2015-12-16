package com.classic.core.utils;

import android.os.Environment;
import android.os.StatFs;
import java.io.File;

/**
 * SD卡工具类
 * @author:续写经典
 * @createtime:2015/11/4
 */
public final class SDcardUtil {
  private SDcardUtil() {
  }

  /** 默认根目录名称 */
  private static String ROOT_DIR = "download";
  /** 默认图片目录名称 */
  public static final String IMAGE_DIR = "images";
  /** 默认文件目录名称 */
  public static final String FILE_DIR = "files";
  /** 默认缓存目录名称 */
  public static final String CACHE_DIR = "cache";
  /** 默认APK目录名称 */
  public static final String APK_DIR = "apk";
  /** 默认DB目录名称 */
  public static final String DB_DIR = "db";
  /** 默认音频目录名称 */
  public static final String AUDIO_DIR = "audio";
  /** 默认视频目录名称 */
  public static final String VIDEO_DIR = "video";
  /** 默认书籍目录名称 */
  public static final String BOOK_DIR = "books";
  /** 默认日志目录名称 */
  public static final String LOG_DIR = "logs";

  /** 默认根目录 */
  private static String rootDir = null;
  /** 默认图片目录 */
  private static String imageDir = null;
  /** 默认文件目录 */
  private static String fileDir = null;
  /** 默认缓存目录 */
  private static String cacheDir = null;
  /** 默认apk文件的目录 */
  private static String apkDir = null;
  /** 默认数据库文件的目录 */
  private static String dbDir = null;
  /** 默认音频的目录 */
  private static String audioDir = null;
  /** 默认视频的目录 */
  private static String videoDir = null;
  /** 默认书籍的目录 */
  private static String bookDir = null;
  /** 默认日志的目录 */
  private static String logDir = null;

  /**
   * 设置SD卡文件存放根目录名称
   * @param dirName
   */
  public static void setRootDirName(String dirName){
    ROOT_DIR = dirName;
  }

  /**
   * SD卡是否可用
   * @return true:可用,false:不可用
   */
  public static boolean isCanUseSD() {
    try {
      return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * 初始化目录
   */
  public static void initDir() {

    //默认根目录
    String downloadRootPath = File.separator + ROOT_DIR + File.separator ;
    //默认图片目录
    String imageDownloadPath = downloadRootPath + IMAGE_DIR + File.separator;
    //默认文件目录
    String fileDownloadPath = downloadRootPath + FILE_DIR + File.separator;
    //默认缓存目录
    String cacheDownloadPath = downloadRootPath + CACHE_DIR + File.separator;
    //默认apk目录
    String apkDownloadPath = downloadRootPath + APK_DIR + File.separator;
    //默认DB目录
    String dbDownloadPath = downloadRootPath + DB_DIR + File.separator;
    //默认音频目录
    String audioDownloadPath = downloadRootPath + AUDIO_DIR + File.separator;
    //默认视频目录
    String videoDownloadPath = downloadRootPath + VIDEO_DIR + File.separator;
    //默认书籍目录
    String bookDownloadPath = downloadRootPath + BOOK_DIR + File.separator;
    //默认日志目录
    String logDownloadPath = downloadRootPath + LOG_DIR + File.separator;

    try {
      if (isCanUseSD()) {
        File root = Environment.getExternalStorageDirectory();

        rootDir = checkDir(root.getAbsolutePath() + downloadRootPath);
        cacheDir = checkDir(root.getAbsolutePath() + cacheDownloadPath);
        imageDir = checkDir(root.getAbsolutePath() + imageDownloadPath);
        fileDir = checkDir(root.getAbsolutePath() + fileDownloadPath);
        apkDir = checkDir(root.getAbsolutePath() + apkDownloadPath);
        dbDir = checkDir(root.getAbsolutePath() + dbDownloadPath);
        audioDir = checkDir(root.getAbsolutePath() + audioDownloadPath);
        videoDir = checkDir(root.getAbsolutePath() + videoDownloadPath);
        bookDir = checkDir(root.getAbsolutePath() + bookDownloadPath);
        logDir = checkDir(root.getAbsolutePath() + logDownloadPath);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  private static String checkDir(String path){
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    return file.getPath();
  }

  /**
   * 计算sdcard上的剩余空间.
   *
   * @return the int
   */
  public static int freeSpaceOnSD() {
    StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
    double sdFreeMB =
        ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / 1024 * 1024;
    return (int) sdFreeMB;
  }

  /**
   * 获取下载根目录
   */
  public static String getRootDir() {
    if (rootDir == null) {
      initDir();
    }
    return rootDir;
  }

  /**
   * 获取下载图片文件目录
   */
  public static String getImageDir() {
    if (imageDir == null) {
      initDir();
    }
    return imageDir;
  }

  /**
   * 获取下载文件目录
   */
  public static String getFileDir() {
    if (fileDir == null) {
      initDir();
    }
    return fileDir;
  }

  /**
   * 获取缓存目录
   */
  public static String getCacheDir() {
    if (cacheDir == null) {
      initDir();
    }
    return cacheDir;
  }

  /**
   * 获取apk文件的目录
   */
  public static String getApkDir() {
    if (apkDir == null) {
      initDir();
    }
    return apkDir;
  }

  /**
   * 获取数据库文件的目录
   */
  public static String getDbDir() {
    if (dbDir == null) {
      initDir();
    }
    return dbDir;
  }

  /**
   * 获取音频文件的目录
   */
  public static String getAudioDir() {
    if (audioDir == null) {
      initDir();
    }
    return audioDir;
  }

  /**
   * 获取视频文件的目录
   */
  public static String getVideoDir() {
    if (videoDir == null) {
      initDir();
    }
    return videoDir;
  }

  /**
   * 获取书籍文件的目录
   */
  public static String getBookDir() {
    if (bookDir == null) {
      initDir();
    }
    return bookDir;
  }

  /**
   * 获取日志文件的目录
   */
  public static String getLogDir() {
    if (logDir == null) {
      initDir();
    }
    return logDir;
  }
}
