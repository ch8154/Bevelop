/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalan.bevelop.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;

/**
 *
 * @author user
 */
public class BitmapUtils {

    /**
     *
     * @param radius
     * @return
     * @deprecated Change to use ViewUtils.
     */
    @Deprecated
    public static GradientDrawable getShapeDrawable(float radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(Color.TRANSPARENT);
        shape.setCornerRadius(radius);
        return shape;
    }

    /**
     * 圓角轉換函式，帶入Bitmap圖片及圓角數值則回傳圓角圖，回傳Bitmap再置入ImageView
     *
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        return getCircleBitmap(bitmap, true);
    }

    public static Bitmap getCircleBitmap(Bitmap bitmap, boolean recycleable) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        if (recycleable) {
            bitmap.recycle();
        }
        return output;
    }

    /**
     * 合成兩張bitmap
     *
     * @param bmp1
     * @param bmp2
     * @return
     */
    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        return overlay(bmp1, bmp2, 0, 0);
    }

    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2, float left, float top) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, left, top, null);
        return bmOverlay;
    }
}
