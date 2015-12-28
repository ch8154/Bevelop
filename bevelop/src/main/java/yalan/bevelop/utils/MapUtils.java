package yalan.bevelop.utils;

import android.location.Location;


import java.math.BigDecimal;

/**
 * Created by Alan Ding on 2016/3/2.
 */
public class MapUtils {

    /**
     * 計算兩點距離
     *
     * @return result[0]:公尺(m)/result[1]:公里(km)
     */
    public static String[] getDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        float[] results = new float[1];
        Location.distanceBetween(latitude1, longitude1, latitude2, longitude2, results);
        String[] stirngResult = new String[2];
        stirngResult[0] = String.valueOf((int) (results[0] * 1.609344));
        BigDecimal m = new BigDecimal((results[0] * 1.609344));
        stirngResult[1] = String.valueOf(m.divide(new BigDecimal(1000f), 2, BigDecimal.ROUND_HALF_UP));
        return stirngResult;
    }
}
