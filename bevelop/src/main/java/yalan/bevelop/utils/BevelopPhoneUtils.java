/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalan.bevelop.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author user
 */
public class BevelopPhoneUtils {

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static String getNetworkOperatorName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperatorName();
    }

    public static String getLocalIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("bevelop", null, ex);
        }
        return "";
    }
//
//    public static LatLng getDefaultPosition(Context c) {
//        Location loc = GPSUtil.getLastPosition(c);
//        if (loc == null) {
//            return getTaipei();
//        }
//        LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
//        return latLng;
//    }
//
//    protected static LatLng getTaipei() {
//        LatLng latLng = new LatLng(25.000000, 121.500000);
//        return latLng;
//    }

    public static int getNavigationBarHeight(Context c) {
        int result = 0;
        boolean hasMenuKey = ViewConfiguration.get(c).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            //The device has a navigation bar
            Resources resources = c.getResources();

            int orientation = resources.getConfiguration().orientation;
            int resourceId;
            if (isTablet(c)) {
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
            } else {
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_width", "dimen", "android");
            }

            if (resourceId > 0) {
                return resources.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    private static boolean isTablet(Context c) {
        return (c.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
