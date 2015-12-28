package yalan.bevelop.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import java.util.ArrayList;

import yalan.bevelop.R;
import yalan.bevelop.utils.ViewUtils;

/**
 * Created by Alan Ding on 2016/2/5.
 */
public class ObjectPickerPopupWindow<T extends Object> implements PopupWindow.OnDismissListener {
    private View parentView;
    private Context context;
    private PopupWindow popupWindow;
    private ListView listView;
    private ArrayList<T> data;
    private TextView textView;
    private T pickedData = null;
    private int listItemLayout = R.layout.view_listitem_popup_text;

    public ObjectPickerPopupWindow(View parentView, int textViewResId, ArrayList<T> arrayList, int firstPickIndex) {
        this.parentView = parentView;
        if (textViewResId != 0) {
            textView = (TextView) parentView.findViewById(textViewResId);
        }
        if (arrayList != null && !arrayList.isEmpty() && arrayList.size() >= firstPickIndex + 1) {
            pickedData = arrayList.get(firstPickIndex);
        }
        context = parentView.getContext();
        this.data = arrayList;
    }

    public String getShowText(T object) {
        return "";
    }

    public void setListItemLayout(int listItemLayout) {
        this.listItemLayout = listItemLayout;
    }

    public void show() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.popup_bonus_count, null);

        ArrayAdapter<T> adapter = new ArrayAdapter<>(context,
                listItemLayout,
                R.id.item, data);
        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pickedData = data.get(position);
                if (textView != null) {
                    textView.setText(getShowText(data.get(position)));
                }
                ObjectPickerPopupWindow.this.dismiss();
            }
        });

        popupWindow = new PopupWindow(view, parentView.getWidth(),
                LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_round_stroke_popupwindow));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(this);
        if (parentView != null) {
            if (!popupWindow.isShowing()) {
                popupWindow.showAsDropDown(parentView, 0, (int) ViewUtils.dpToPixel(context, -6));
            }
        }
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public boolean isShowing() {
        return popupWindow != null && popupWindow.isShowing();
    }

    @Override
    public void onDismiss() {

    }

    /**
     * @return　預設值為傳入之TextView的Text
     */
    public T getPickedData() {
        return pickedData;
    }

    public T getPickedData(T defultValue) {
        return pickedData.equals("") ? defultValue : pickedData;
    }
}
