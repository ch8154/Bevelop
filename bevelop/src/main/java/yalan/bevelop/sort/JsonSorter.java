/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalan.bevelop.sort;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class JsonSorter {

    ArrayList<JSONObject> arr;

    public JsonSorter(ArrayList<JSONObject> list) {
        this.arr = list;
    }

    public JsonSorter(JSONArray jsonArray) {
        this.arr = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject j = jsonArray.optJSONObject(i);
            arr.add(j);
        }
    }

    public JSONArray sort(final String key, final boolean isAtoB) {
        Collections.sort(arr, new Comparator<JSONObject>() {

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = (String) a.get(key);
                    valB = (String) b.get(key);
                } catch (JSONException e) {
                    Log.e("Bevelop", null, e);
                }
                if (isAtoB) {
                    return valA.compareTo(valB);
                } else {
                    return -valA.compareTo(valB);
                }
            }
        }
        );
        JSONArray returnArray = new JSONArray();

        for (JSONObject obj : arr) {
            returnArray.put(obj);
        }
        return returnArray;
    }

    public ArrayList<JSONObject> sortToArrayList(final String key, final boolean isAtoB) {
        Collections.sort(arr, new Comparator<JSONObject>() {

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = (String) a.get(key);
                    valB = (String) b.get(key);
                } catch (JSONException e) {
                    Log.e("Bevelop", null, e);
                }
                if (isAtoB) {
                    return valA.compareTo(valB);
                } else {
                    return -valA.compareTo(valB);
                }
            }
        }
        );
        return arr;
    }
}
