/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalan.bevelop.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import grandroid.action.Action;
import grandroid.dialog.DialogMask;
import grandroid.dialog.GDialog;
import grandroid.view.LayoutMaker;
import grandroid.view.StyledText;
import java.util.Calendar;

/**
 * 體重Pickerdiolog
 *
 * @author Alan
 */
public abstract class KGPickerDialog extends DialogMask {

    protected int i, d, limit;
    protected NumberPicker npI, npD;
    protected int textColor = Color.BLACK;
    protected double v;

    public KGPickerDialog(Context context, int i, int d, int limit) {
        super(context);
        this.i = i;
        this.d = d;
        this.limit = limit;
    }

    @Override
    public boolean setupMask(Context context, GDialog.Builder builder, LayoutMaker maker) throws Exception {
        npI = new NumberPicker(context);
        npI.setMaxValue(limit);
        npI.setMinValue(0);
        npI.setValue(i);
        npI.setWrapSelectorWheel(false);

        npD = new NumberPicker(context);
        npD.setMaxValue(9);
        npD.setMinValue(0);
        npD.setValue(d);
        builder.setTitle("選擇體重");
        maker.addRowLayout(false, maker.layFW());
        {
            ((LinearLayout) maker.getLastLayout()).setGravity(Gravity.CENTER_HORIZONTAL);
            maker.add(npI, maker.layWW(0));
            maker.add(maker.createStyledText(".").padding(0, 0, 20, 0).color(textColor).center().size(StyledText.Unit.Auto, 30).get(), maker.layWF(0));
            maker.add(npD, maker.layWW(0));
            maker.add(maker.createStyledText("kg").color(textColor).center().size(StyledText.Unit.Auto, 30).get(), maker.layWF(0));
            maker.escape();
        }
        builder.setNegativeButton(new Action("Done") {

            @Override
            public boolean execute() {
                String value = String.valueOf(npI.getValue()) + "." + String.valueOf(npD.getValue());
                v = Double.valueOf(value);
                onDateSet(npI.getValue(), npD.getValue(), v);
                return true;
            }

        });
        builder.setPositiveButton(new Action("Cancel"));
        return true;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setKG(int i) {
        this.i = i;
    }

    public void setG(int d) {
        this.d = d;
    }

    public double getValue() {
        return v;
    }

    public abstract void onDateSet(int i, int d, double value);
}
