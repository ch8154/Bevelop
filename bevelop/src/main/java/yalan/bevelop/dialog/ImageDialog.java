/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalan.bevelop.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import grandroid.dialog.DialogMask;
import grandroid.dialog.GDialog;
import grandroid.fancyview.zoomableimage.ImageZoomView;
import grandroid.view.LayoutMaker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author user
 */
public class ImageDialog extends DialogMask {

    protected Bitmap bmp;
    protected int drawableDesignWidth;
    private int requiredSize = 720;

    public ImageDialog(Context context, File file, int drawableDesignWidth) {
        super(context);
        bmp = decodeFile(file);
        this.drawableDesignWidth = drawableDesignWidth;
    }

    public ImageDialog(Context context, Bitmap bmp, int drawableDesignWidth) {
        super(context);
        this.bmp = bmp;
        this.drawableDesignWidth = drawableDesignWidth;
    }

    public ImageDialog(Context context, File file, int drawableDesignWidth, int requiredSize) {
        super(context);
        bmp = decodeFile(file);
        this.drawableDesignWidth = drawableDesignWidth;
        this.requiredSize = requiredSize;
    }

    @Override
    public boolean setupMask(Context context, GDialog.Builder builder, LayoutMaker maker) throws Exception {
        maker.setDrawableDesignWidth(((Activity) context), drawableDesignWidth);
        maker.addColLayout(false, maker.layAbsolute(0, 0, drawableDesignWidth, LinearLayout.LayoutParams.MATCH_PARENT));
        {
            ((LinearLayout) maker.getLastLayout()).setGravity(Gravity.CENTER);
            maker.getLastLayout().setBackgroundColor(Color.BLACK);
//            maker.getLastLayout().setOnClickListener(new View.OnClickListener() {
//
//                public void onClick(View arg0) {
//
//                }
//            });
            ImageZoomView ivPhoto = new ImageZoomView(context);
            ivPhoto.setImage(bmp);
            ivPhoto.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });
            maker.add(ivPhoto, maker.layFF());
            maker.escape();
        }
        return true;
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        bmp.recycle();
        bmp = null;
    }

    private Bitmap decodeFile(File f) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            Log.e("grandroid", null, e);
        }
        return null;
    }

    public void setRequiredSize(int requiredSize) {
        this.requiredSize = requiredSize;
    }

}
