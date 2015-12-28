/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalan.bevelop.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 *
 * @author user
 */
public class CheckableDatePickerDialog extends DatePickerDialog {

    protected int year;
    protected int month;
    protected int day;

    public CheckableDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        super.onClick(dialog, which); //To change body of generated methods, choose Tools | Templates.
        onCheck(getDatePicker(), getDatePicker().getYear(), getDatePicker().getMonth(), getDatePicker().getDayOfMonth());
    }

    public boolean onCheck(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        return true;
    }
}
