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


    /** 图片最大宽度. */
    public static final int MAX_WIDTH = 4096 / 2;
    /** 图片最大高度. */
    public static final int MAX_HEIGHT = 4096 / 2;


    /**
     * 获取Bitmap大小.
     *
     * @param bitmap the bitmap
     * @param mCompressFormat 图片格式 Bitmap.CompressFormat.JPEG,CompressFormat.PNG
     * @return 图片的大小
     */
    public static int getByteCount(Bitmap bitmap, Bitmap.CompressFormat mCompressFormat) {
        int size = 0;
        ByteArrayOutputStream output = null;
        try {
            output = new ByteArrayOutputStream();
            bitmap.compress(mCompressFormat, 100, output);
            byte[] result = output.toByteArray();
            size = result.length;
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(output);
        }
        return size;
    }


    /**
     * 描述：获取图片尺寸
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
        return new BitmapDrawable(bitmap);
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
    public static TransitionDrawable bitmapToTransitionDrawable(Bitmap bitmap) {
        TransitionDrawable mBitmapDrawable = null;
        try {
            if (bitmap == null) {
                return null;
            }
            mBitmapDrawable = new TransitionDrawable(
                    new Drawable[] { new ColorDrawable(android.R.color.transparent),
                            new BitmapDrawable(bitmap) });
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
    public static TransitionDrawable drawableToTransitionDrawable(Drawable drawable) {
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
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }


    /**
     * 描述：缩放图片.(超出的裁掉)
     *
     * @param file File对象
     * @param desiredWidth 新图片的宽
     * @param desiredHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getScaleBitmap(File file, int desiredWidth, int desiredHeight) {

        Bitmap resizeBmp = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);

        // 获取图片的原始宽度高度
        int srcWidth = opts.outWidth;
        int srcHeight = opts.outHeight;

        //需要的尺寸重置
        int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        desiredWidth = size[0];
        desiredHeight = size[1];

        // 默认为ARGB_8888.
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        // 以下两个字段需一起使用：
        // 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
        opts.inPurgeable = true;
        // 位图可以共享一个参考输入数据(inputstream、阵列等)
        opts.inInputShareable = true;
        // 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
        int sampleSize = findBestSampleSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        opts.inSampleSize = sampleSize;
        // 创建内存
        opts.inJustDecodeBounds = false;
        // 使图片不抖动
        opts.inDither = false;

        resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);

        // 缩放的比例
        float scale = getMinScale(resizeBmp.getWidth(), resizeBmp.getHeight(), desiredWidth,
                desiredHeight);
        if (scale < 1) {
            // 缩小
            resizeBmp = scaleBitmap(resizeBmp, scale);
        }

        //超出的裁掉
        if (resizeBmp.getWidth() > desiredWidth || resizeBmp.getHeight() > desiredHeight) {
            resizeBmp = getCutBitmap(resizeBmp, desiredWidth, desiredHeight);
        }
        return resizeBmp;
    }


    /**
     * 描述：缩放图片.(超出的裁掉)
     *
     * @param bitmap the bitmap
     * @param desiredWidth 新图片的宽
     * @param desiredHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getScaleBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {

        if (!checkBitmap(bitmap)) {
            return null;
        }
        Bitmap resizeBmp = null;

        // 获得图片的宽高
        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();

        int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        desiredWidth = size[0];
        desiredHeight = size[1];

        float scale = getMinScale(srcWidth, srcHeight, desiredWidth, desiredHeight);
        resizeBmp = scaleBitmap(bitmap, scale);
        //超出的裁掉
        if (resizeBmp.getWidth() > desiredWidth || resizeBmp.getHeight() > desiredHeight) {
            resizeBmp = getCutBitmap(resizeBmp, desiredWidth, desiredHeight);
        }
        return resizeBmp;
    }


    /**
     * 描述：裁剪图片.
     *
     * @param file File对象
     * @param desiredWidth 新图片的宽
     * @param desiredHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getCutBitmap(File file, int desiredWidth, int desiredHeight) {

        Bitmap resizeBmp = null;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);

        // 获取图片的原始宽度
        int srcWidth = opts.outWidth;
        // 获取图片原始高度
        int srcHeight = opts.outHeight;

        int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        desiredWidth = size[0];
        desiredHeight = size[1];

        // 默认为ARGB_8888.
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        // 以下两个字段需一起使用：
        // 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
        opts.inPurgeable = true;
        // 位图可以共享一个参考输入数据(inputstream、阵列等)
        opts.inInputShareable = true;
        // 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
        int sampleSize = findBestSampleSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        opts.inSampleSize = sampleSize;
        // 创建内存
        opts.inJustDecodeBounds = false;
        // 使图片不抖动
        opts.inDither = false;
        resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);

        if (resizeBmp != null) {
            resizeBmp = getCutBitmap(resizeBmp, desiredWidth, desiredHeight);
        }
        return resizeBmp;
    }


    /**
     * 描述：裁剪图片.
     *
     * @param bitmap the bitmap
     * @param desiredWidth 新图片的宽
     * @param desiredHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getCutBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {

        if (!checkBitmap(bitmap)) {
            return null;
        }

        if (!checkSize(desiredWidth, desiredHeight)) {
            return null;
        }

        Bitmap resizeBmp = null;

        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            int offsetX = 0;
            int offsetY = 0;

            if (width > desiredWidth) {
                offsetX = (width - desiredWidth) / 2;
            }
            else {
                desiredWidth = width;
            }

            if (height > desiredHeight) {
                offsetY = (height - desiredHeight) / 2;
            }
            else {
                desiredHeight = height;
            }

            resizeBmp = Bitmap.createBitmap(bitmap, offsetX, offsetY, desiredWidth, desiredHeight);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resizeBmp != bitmap) {
                bitmap.recycle();
            }
        }
        return resizeBmp;
    }


    /**
     * 描述：根据等比例缩放图片.
     *
     * @param bitmap the bitmap
     * @param scale 比例
     * @return Bitmap 新图片
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float scale) {

        if (!checkBitmap(bitmap)) {
            return null;
        }

        if (scale == 1) {
            return bitmap;
        }

        Bitmap resizeBmp = null;
        try {
            // 获取Bitmap资源的宽和高
            int bmpW = bitmap.getWidth();
            int bmpH = bitmap.getHeight();

            // 注意这个Matirx是android.graphics底下的那个
            Matrix matrix = new Matrix();
            // 设置缩放系数，分别为原来的0.8和0.8
            matrix.postScale(scale, scale);
            resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpW, bmpH, matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resizeBmp != bitmap) {
                bitmap.recycle();
            }
        }
        return resizeBmp;
    }


    /**
     * 获取缩小的比例.
     */
    private static float getMinScale(int srcWidth, int srcHeight, int desiredWidth, int desiredHeight) {
        // 缩放的比例
        float scale = 0;
        // 计算缩放比例，宽高的最小比例
        float scaleWidth = (float) desiredWidth / srcWidth;
        float scaleHeight = (float) desiredHeight / srcHeight;
        if (scaleWidth > scaleHeight) {
            scale = scaleWidth;
        }
        else {
            scale = scaleHeight;
        }

        return scale;
    }


    private static int[] resizeToMaxSize(int srcWidth, int srcHeight, int desiredWidth, int desiredHeight) {
        int[] size = new int[2];
        if (desiredWidth <= 0) {
            desiredWidth = srcWidth;
        }
        if (desiredHeight <= 0) {
            desiredHeight = srcHeight;
        }
        if (desiredWidth > MAX_WIDTH) {
            // 重新计算大小
            desiredWidth = MAX_WIDTH;
            float scaleWidth = (float) desiredWidth / srcWidth;
            desiredHeight = (int) (desiredHeight * scaleWidth);
        }

        if (desiredHeight > MAX_HEIGHT) {
            // 重新计算大小
            desiredHeight = MAX_HEIGHT;
            float scaleHeight = (float) desiredHeight / srcHeight;
            desiredWidth = (int) (desiredWidth * scaleHeight);
        }
        size[0] = desiredWidth;
        size[1] = desiredHeight;
        return size;
    }


    private static boolean checkBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return false;
        }

        if (bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
            return false;
        }
        return true;
    }


    private static boolean checkSize(int desiredWidth, int desiredHeight) {
        if (desiredWidth <= 0 || desiredHeight <= 0) {
            return false;
        }
        return true;
    }


    /**
     * 找到最合适的SampleSize
     */
    private static int findBestSampleSize(int width, int height, int desiredWidth, int desiredHeight) {
        double wr = (double) width / desiredWidth;
        double hr = (double) height / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }
        return (int) n;
    }


    /**
     * 描述：旋转Bitmap为一定的角度并四周暗化处理.
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
        }
        else {
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
     * 释放Bitmap对象.
     *
     * @param bitmap 要释放的Bitmap
     */
    public static void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            } catch (Exception e) {
            }
            bitmap = null;
        }
    }


    /**
     * 释放Bitmap数组.
     *
     * @param bitmaps 要释放的Bitmap数组
     */
    public static void releaseBitmapArray(Bitmap[] bitmaps) {
        if (bitmaps != null) {
            try {
                for (Bitmap bitmap : bitmaps) {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
            } catch (Exception e) {
            }
        }
    }


    private static int mDesiredWidth;
    private static int mDesiredHeight;


    /**
     * 从Resources中加载图片
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置成了true,不占用内存，只获取bitmap宽高
        options.inJustDecodeBounds = true;
        // 初始化options对象
        BitmapFactory.decodeResource(res, resId, options);
        // 得到计算好的options，目标宽、目标高
        options = getBestOptions(options, reqWidth, reqHeight);
        Bitmap src = BitmapFactory.decodeResource(res, resId, options); // 载入一个稍大的缩略图
        return createScaleBitmap(src, mDesiredWidth, mDesiredHeight); // 进一步得到目标大小的缩略图
    }


    /**
     * 从SD卡上加载图片
     */
    public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options = getBestOptions(options, reqWidth, reqHeight);
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return createScaleBitmap(src, mDesiredWidth, mDesiredHeight);
    }


    /**
     * 计算目标宽度，目标高度，inSampleSize
     *
     * @return BitmapFactory.Options对象
     */
    private static BitmapFactory.Options getBestOptions(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 读取图片长宽
        int actualWidth = options.outWidth;
        int actualHeight = options.outHeight;
        // Then compute the dimensions we would ideally like to decode to.
        mDesiredWidth = getResizedDimension(reqWidth, reqHeight, actualWidth, actualHeight);
        mDesiredHeight = getResizedDimension(reqHeight, reqWidth, actualHeight, actualWidth);
        // 根据现在得到计算inSampleSize
        options.inSampleSize = calculateBestInSampleSize(actualWidth, actualHeight, mDesiredWidth,
                mDesiredHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return options;
    }


    /**
     * Scales one side of a rectangle to fit aspect ratio. 最终得到重新测量的尺寸
     *
     * @param maxPrimary Maximum size of the primary dimension (i.e. width for max
     * width), or zero to maintain aspect ratio with secondary
     * dimension
     * @param maxSecondary Maximum size of the secondary dimension, or zero to maintain
     * aspect ratio with primary dimension
     * @param actualPrimary Actual size of the primary dimension
     * @param actualSecondary Actual size of the secondary dimension
     */
    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary, int actualSecondary) {
        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;
        if (resized * ratio > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }


    /**
     * Returns the largest power-of-two divisor for use in downscaling a bitmap
     * that will not result in the scaling past the desired dimensions.
     *
     * @param actualWidth Actual width of the bitmap
     * @param actualHeight Actual height of the bitmap
     * @param desiredWidth Desired width of the bitmap
     * @param desiredHeight Desired height of the bitmap
     */
    // Visible for testing.
    private static int calculateBestInSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float inSampleSize = 1.0f;
        while ((inSampleSize * 2) <= ratio) {
            inSampleSize *= 2;
        }

        return (int) inSampleSize;
    }


    /**
     * 通过传入的bitmap，进行压缩，得到符合标准的bitmap
     */
    private static Bitmap createScaleBitmap(Bitmap tempBitmap, int desiredWidth, int desiredHeight) {
        // If necessary, scale down to the maximal acceptable size.
        if (tempBitmap != null &&
                (tempBitmap.getWidth() > desiredWidth || tempBitmap.getHeight() > desiredHeight)) {
            // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
            Bitmap bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight,
                    true);
            tempBitmap.recycle(); // 释放Bitmap的native像素数组
            return bitmap;
        }
        else {
            return tempBitmap; // 如果没有缩放，那么不回收
        }
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
            {
                java.io.InputStream is;
                is = new FileInputStream(pathString);
                BitmapFactory.Options opts = getOptionsWithInSampleSize(pathString, dstMaxWH);
                srcBitmap = BitmapFactory.decodeStream(is, null, opts);
                if (srcBitmap == null) return retBm;
            }
            // 原图片宽高
            int width = srcBitmap.getWidth();
            int height = srcBitmap.getHeight();
            // 获得缩放因子
            float scale = 1.f;
            {
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
            }
            // 图片缩放
            Bitmap dstBitmap;
            if (scale == 1.f) {
                dstBitmap = srcBitmap;
            }
            else {
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
}
