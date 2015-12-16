package com.classic.core.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Assets、Raw资源文件操作工具类
 * @author 续写经典
 * @date 2015/11/3
 */
public final class ResourceUtil {
    private ResourceUtil() { }
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 读取Assets下的字符串列表
     * @param context
     * @param fileName
     * @return
     */
    public static List<String> getListFromAssets(Context context, String fileName) {
        if (context == null || DataUtil.isEmpty(fileName)) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(
                fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取Raw下的字符串列表
     * @param context
     * @param resId
     * @return
     */
    public static List<String> getListFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            reader = new BufferedReader(in);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 描述：读取Assets目录的文件内容.(默认UTF-8编码)
     *
     * @param context the context
     * @param name the name
     * @return string
     */
    public static String readAssetsByName(Context context,String name){
        return readAssetsByName(context, name,DEFAULT_ENCODING);
    }
    /**
     * 描述：读取Assets目录的文件内容.
     *
     * @param context the context
     * @param name the name
     * @param encoding the encoding
     * @return the string
     */
    public static String readAssetsByName(Context context,String name,String encoding){
        if (context == null || DataUtil.isEmpty(name)) {
            return null;
        }
        String text = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getAssets().open(name));
            bufReader = new BufferedReader(inputReader);
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while((line = bufReader.readLine()) != null){
                buffer.append(line);
            }
            text = new String(buffer.toString().getBytes(), encoding);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            CloseUtil.close(bufReader,inputReader);
        }
        return text;
    }

    /**
     * 描述：读取Raw目录的文件内容.(默认UTF-8编码)
     *
     * @param context the context
     * @param id the id
     * @return string
     */
    public static String readRawByName(Context context,int id){
        return readRawByName(context, id,DEFAULT_ENCODING);
    }
    /**
     * 描述：读取Raw目录的文件内容.
     *
     * @param context the context
     * @param id the id
     * @param encoding the encoding
     * @return the string
     */
    public static String readRawByName(Context context,int id,String encoding){
        String text = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources().openRawResource(id));
            bufReader = new BufferedReader(inputReader);
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while((line = bufReader.readLine()) != null){
                buffer.append(line);
            }
            text = new String(buffer.toString().getBytes(),encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            CloseUtil.close(bufReader,inputReader);
        }
        return text;
    }

    /**
     * 描述：获取Asset中的图片资源.
     *
     * @param context the context
     * @param fileName the file name
     * @return Bitmap 图片
     */
    public static Bitmap getBitmapFromAsset(Context context,String fileName){
        Bitmap bit = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fileName);
            bit = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bit;
    }

    /**
     * 描述：获取Asset中的图片资源.
     *
     * @param context the context
     * @param fileName the file name
     * @return Drawable 图片
     */
    public static Drawable getDrawableFromAsset(Context context,String fileName){
        Drawable drawable = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fileName);
            drawable = Drawable.createFromStream(is, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }


    /**
     * 拷贝Assets目录内容到sd卡目录
     * @param context
     * @param assetDir
     * @param outDir    完整sd卡路径
     */
    public static void copyAssets2SD(Context context, String assetDir, String outDir) {
        String[] files;
        try {
            files = context.getAssets().list(assetDir);
            File outDirFile = new File(outDir);
            if (!outDirFile.exists()) {
                outDirFile.mkdirs();
            }

            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];

                String[] filesChild = context.getAssets().list(fileName);
                if (filesChild!=null && filesChild.length>0) {
                    copyAssets2SD(context, fileName, outDir + "/"+fileName);
                } else {
                    InputStream in = null;
                    if(!TextUtils.isEmpty(assetDir)){
                        in = context.getAssets().open(assetDir + "/" + fileName);
                    }else{
                        in = context.getAssets().open(fileName);
                    }
                    File outFile = new File(outDir+"/"+fileName);
                    if(outFile.exists()){
                        outFile.delete();
                    }
                    outFile.createNewFile();
                    OutputStream out = new FileOutputStream(outFile);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    in.close();
                    out.close();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
