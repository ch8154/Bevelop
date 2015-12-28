/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalan.bevelop.utils;

import android.graphics.drawable.GradientDrawable;

/**
 *
 * @author user
 */
public class ViewUtils {

    public static GradientDrawable getGradientDrawable(float[] radiuss) {
        return getGradientDrawable(radiuss, 0);
    }

    public static GradientDrawable getGradientDrawable(float[] radiuss, int color) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadii(radiuss);
        shape.setColor(color);
        return shape;
    }

    public static GradientDrawable getGradientDrawable(int radius, int color) {
        GradientDrawable shape = new GradientDrawable();

        shape.setCornerRadius(radius);
        shape.setColor(color);
        return shape;
    }

    public static GradientDrawable getGradientDrawable(int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(radius);
        return shape;
    }
}
