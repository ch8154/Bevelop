/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalan.bevelop.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import grandroid.adapter.SimpleRowAdapter;
import grandroid.view.LayoutMaker;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class JSONSimpleRowAdapter extends SimpleRowAdapter<JSONObject> {

    protected String showByKey = "name";
    protected int designWidth = 640;

    public JSONSimpleRowAdapter(int designWidth, String showByKey) {
        this.showByKey = showByKey;
        this.designWidth = designWidth;
    }

    @Override
    public void onClickItem(int index, View view, JSONObject item) {
        super.onClickItem(index, view, item); //To change body of generated methods, choose Tools | Templates.
    }

    public View createRowView(Context context, int index, JSONObject item) {
        LinearLayout ll = new LinearLayout(context);
        LayoutMaker m = new LayoutMaker(context, ll, false);
        m.setDrawableDesignWidth((Activity) context, designWidth);
        m.setScalablePadding(m.getLastLayout(), 0, 10, 0, 10);
        m.add(m.createStyledText("").tag("NAME").color(Color.WHITE).padding(20, 0, 20, 0).get(), m.layFW());
        return ll;
    }

    public void fillRowView(Context context, int index, View cellRenderer, JSONObject item) throws Exception {
        findView(cellRenderer, "NAME", TextView.class).setText(item.optString(showByKey));

    }

}
