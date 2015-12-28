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
 * 民國年DatePickerdiolog
 *
 * @author Alan
 */
public abstract class ROCDatePickerDialog extends DialogMask {

    protected int year, month, day;
    protected NumberPicker npYear, npMonth, npDay;
    protected int feburaryDays = 28;
    protected Calendar c;
    protected int rangeOfYears = 10;

    public ROCDatePickerDialog(Context context, int year, int month, int day) {
        super(context);
        this.c = Calendar.getInstance();
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     *
     * @param context
     * @param year
     * @param month
     * @param day
     * @param rangeOfYears 年份範圍 現在年分 ~ (現在年分-yearRange)
     */
    public ROCDatePickerDialog(Context context, int year, int month, int day, int rangeOfYears) {
        this(context, year, month, day);
        this.rangeOfYears = rangeOfYears;
    }

    @Override
    public boolean setupMask(Context context, GDialog.Builder builder, LayoutMaker maker) throws Exception {
        npYear = new NumberPicker(context);
        npYear.setMaxValue(c.get(Calendar.YEAR) - 1911);
        npYear.setMinValue(c.get(Calendar.YEAR) - 1911 - rangeOfYears);
        npYear.setValue(year);
        npYear.setWrapSelectorWheel(false);
        npYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //105閏年 每四年一閏
                if (isLeapYear(newVal)) {
                    feburaryDays = 29;
                    if (npMonth.getValue() == 2) {
                        npDay.setMaxValue(feburaryDays);
                    }
                } else {
                    feburaryDays = 28;
                    if (npMonth.getValue() == 2) {
                        if (npDay.getValue() == 29) {
                            npDay.setValue(feburaryDays);
                        }
                        npDay.setMaxValue(feburaryDays);
                    }
                }
            }
        });
        npMonth = new NumberPicker(context);
        npMonth.setMaxValue(12);
        npMonth.setMinValue(1);
        npMonth.setValue(month);
        npMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                switch (newVal) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        npDay.setMaxValue(31);
                        break;
                    case 2:
                        if (npDay.getValue() < feburaryDays) {
                            npDay.setValue(feburaryDays);
                        }
                        npDay.setMaxValue(feburaryDays);
                        break;
                    default:
                        npDay.setMaxValue(30);
                        break;
                }
            }
        });
        npDay = new NumberPicker(context);
        npDay.setMaxValue(31);
        npDay.setMinValue(1);
        npDay.setValue(day);
        maker.addRowLayout(false, maker.layFW());
        {
            ((LinearLayout) maker.getLastLayout()).setGravity(Gravity.CENTER_HORIZONTAL);
            maker.add(maker.createStyledText("民國").padding(0, 0, 8, 0).color(Color.WHITE).center().size(StyledText.Unit.Auto, 30).get(), maker.layWF(0));
            maker.add(npYear, maker.layWW(0));
            maker.add(maker.createStyledText(".").color(Color.WHITE).center().size(StyledText.Unit.Auto, 30).get(), maker.layWF(0));
            maker.add(npMonth, maker.layWW(0));
            maker.add(maker.createStyledText(".").color(Color.WHITE).center().size(StyledText.Unit.Auto, 30).get(), maker.layWF(0));
            maker.add(npDay, maker.layWW(0));
            maker.escape();
        }
        builder.setTitle("請選擇日期");
        builder.setNegativeButton(new Action("確定") {

            @Override
            public boolean execute() {
                onDateSet(npYear.getValue(), npMonth.getValue(), npDay.getValue());
                return true;
            }

        });
        builder.setPositiveButton(new Action("取消"));
        return true;
    }

    private boolean isLeapYear(int y) {
        return (y - 105) % 4 == 0;
    }

    public abstract void onDateSet(int year, int month, int day);
}
