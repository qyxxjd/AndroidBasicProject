package com.classic.core.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * bitmap工具类
 *
 * @author 续写经典
 * @version 1.0 2015/11/4
 */
public final class BitmapUtil {
    private BitmapUtil() {}

    /**
     * 获取图片尺寸
     *
     * @param file File对象
     * @return 尺寸数组. 0:width,1:height
     */
    public static float[] getBitmapSize(File file) {
        float[] size = new float[2];
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);
        // 获取图片的原始宽度高度
        size[0] = opts.outWidth;
        size[1] = opts.outHeight;
        return size;
    }


    /**
     * Byte[]转Bitmap
     */
    public static Bitmap bytes2Bitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }


    /**
     * Bitmap转Byte[]
     *
     * @param needRecycle 转换完毕后是否需要回收bitmap
     */
    public static byte[] bitmap2Bytes(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        CloseUtil.close(output);
        return result;
    }


    /**
     * Bitmap转Drawable
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return bitmap2Drawable(null, bitmap);
    }

    /**
     * Bitmap转Drawable
     */
    public static Drawable bitmap2Drawable(Resources res, Bitmap bitmap) {
        return new BitmapDrawable(res, bitmap);
    }


    /**
     * Drawable转Bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }


    /**
     * Bitmap对象转换TransitionDrawable对象.
     *
     * @param bitmap 要转化的Bitmap对象 imageView.setImageDrawable(td);
     * td.startTransition(200);
     * @return Drawable 转化完成的Drawable对象
     */
    @SuppressWarnings("ResourceType")
    public static TransitionDrawable bitmap2TransitionDrawable(Bitmap bitmap) {
        TransitionDrawable mBitmapDrawable = null;
        try {
            if (bitmap == null) {
                return null;
            }
            mBitmapDrawable = new TransitionDrawable(
                    new Drawable[] { new ColorDrawable(android.R.color.transparent),
                            new BitmapDrawable(null, bitmap) });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmapDrawable;
    }


    /**
     * Drawable对象转换TransitionDrawable对象.
     *
     * @param drawable 要转化的Drawable对象 imageView.setImageDrawable(td);
     * td.startTransition(200);
     * @return Drawable 转化完成的Drawable对象
     */
    @SuppressWarnings("ResourceType")
    public static TransitionDrawable drawable2TransitionDrawable(Drawable drawable) {
        TransitionDrawable mBitmapDrawable = null;
        try {
            if (drawable == null) {
                return null;
            }
            mBitmapDrawable = new TransitionDrawable(
                    new Drawable[] { new ColorDrawable(android.R.color.transparent), drawable });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmapDrawable;
    }


    /**
     * 将ImageView转换为Bitmap.
     *
     * @param view 要转换为bitmap的View
     * @return byte[] 图片的byte[]
     */
    public static Bitmap imageView2Bitmap(ImageView view) {
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 旋转图像
     *
     * @param bmp Bitmap
     * @param degrees 旋转角度
     */
    public static Bitmap rotateBitmap(Bitmap bmp, int degrees) {
        if (degrees != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees % 360);
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }
        return bmp;
    }


    /**
     * 得到bitmap的大小
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight(); //earlier version
    }


    /**
     * 旋转Bitmap为一定的角度并四周暗化处理.
     *
     * @param bitmap the bitmap
     * @param degrees the degrees
     * @return the bitmap
     */
    public static Bitmap rotateBitmapTranslate(Bitmap bitmap, float degrees) {
        Bitmap mBitmap = null;
        int width;
        int height;
        try {
            Matrix matrix = new Matrix();
            if ((degrees / 90) % 2 != 0) {
                width = bitmap.getWidth();
                height = bitmap.getHeight();
            }
            else {
                width = bitmap.getHeight();
                height = bitmap.getWidth();
            }
            int cx = width / 2;
            int cy = height / 2;
            matrix.preTranslate(-cx, -cy);
            matrix.postRotate(degrees);
            matrix.postTranslate(cx, cy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmap;
    }


    /**
     * 转换图片转换成圆形.
     *
     * @param bitmap 传入Bitmap对象
     * @return the bitmap
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }


    /**
     * 转换图片转换成圆角.
     *
     * @param bitmap 传入Bitmap对象
     * @return the bitmap
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap, int roundPx) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        }
        else {
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }


    /**
     * 转换图片转换成镜面效果的图片.
     *
     * @param bitmap 传入Bitmap对象
     * @return the bitmap
     */
    public static Bitmap toReflectionBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        try {
            int reflectionGap = 1;
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            // This will not scale but will flip on the Y axis
            Matrix matrix = new Matrix();
            matrix.preScale(1, -1);

            // Create a Bitmap with the flip matrix applied to it.
            // We only want the bottom half of the image
            Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2,
                    matrix, false);

            // Create a new bitmap with same width but taller to fit
            // reflection
            Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2),
                    Bitmap.Config.ARGB_8888);

            // Create a new Canvas with the bitmap that's big enough for
            // the image plus gap plus reflection
            Canvas canvas = new Canvas(bitmapWithReflection);
            // Draw in the original image
            canvas.drawBitmap(bitmap, 0, 0, null);
            // Draw in the gap
            Paint deafaultPaint = new Paint();
            canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
            // Draw in the reflection
            canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
            // Create a shader that is a linear gradient that covers the
            // reflection
            Paint paint = new Paint();
            LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                    bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
                    Shader.TileMode.CLAMP);
            // Set the paint to use this shader (linear gradient)
            paint.setShader(shader);
            // Set the Transfer mode to be porter duff and destination in
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            // Draw a rectangle using the paint with our linear gradient
            canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap,
                    paint);

            bitmap = bitmapWithReflection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 等比例缩放图片
     *
     * @param pathString 文件路径
     * @param dstMaxWH 目标文件宽高最大值
     */
    public static Bitmap scaleImageByPath(String pathString, int dstMaxWH) {
        Bitmap retBm = null;
        // 路径为空
        if (TextUtils.isEmpty(pathString) || dstMaxWH <= 0) {
            return retBm;
        }
        File file = new File(pathString);
        if (!file.exists()) {
            return retBm;
        }
        try {
            // 打开源文件
            Bitmap srcBitmap;
            java.io.InputStream is = new FileInputStream(pathString);
            BitmapFactory.Options opts = getOptionsWithInSampleSize(pathString, dstMaxWH);
            srcBitmap = BitmapFactory.decodeStream(is, null, opts);
            if (srcBitmap == null){
                return retBm;
            }
            // 原图片宽高
            int width = srcBitmap.getWidth();
            int height = srcBitmap.getHeight();
            // 获得缩放因子
            float scale = 1.f;
            if (width > dstMaxWH || height > dstMaxWH) {
                float scaleTemp = (float) dstMaxWH / (float) width;
                float scaleTemp2 = (float) dstMaxWH / (float) height;
                if (scaleTemp > scaleTemp2) {
                    scale = scaleTemp2;
                }
                else {
                    scale = scaleTemp;
                }
            }
            // 图片缩放
            Bitmap dstBitmap;
            if (scale == 1.f) {
                dstBitmap = srcBitmap;
            } else {
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                dstBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, matrix, true);
                if (!srcBitmap.isRecycled()) srcBitmap.recycle();
                srcBitmap = null;
            }
            retBm = dstBitmap;
        } catch (Exception e) {
            return retBm;
        }
        return retBm;
    }


    /**
     * 按比例取得缩略图
     */
    public static BitmapFactory.Options getOptionsWithInSampleSize(String filePath, int maxWidth) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;// 只取得outHeight(图片原始高度)和
        // outWidth(图片的原始宽度)而不加载图片
        BitmapFactory.decodeFile(filePath, bitmapOptions);
        bitmapOptions.inJustDecodeBounds = false;
        int inSampleSize = bitmapOptions.outWidth / (maxWidth / 10);// 应该直接除160的，但这里出16是为了增加一位数的精度
        if (inSampleSize % 10 != 0) {
            inSampleSize += 10;// 尽量取大点图片，否则会模糊
        }
        inSampleSize = inSampleSize / 10;
        if (inSampleSize <= 0) {// 判断200是否超过原始图片高度
            inSampleSize = 1;// 如果超过，则不进行缩放
        }
        bitmapOptions.inSampleSize = inSampleSize;
        return bitmapOptions;
    }


    /**
     * 释放Bitmap
     *
     * @param bitmaps
     */
    public static void release(Bitmap... bitmaps) {
        if (bitmaps != null) {
            try {
                for (Bitmap bitmap : bitmaps) {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
