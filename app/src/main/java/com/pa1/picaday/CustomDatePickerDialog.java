package com.pa1.picaday;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

public class CustomDatePickerDialog extends DatePickerDialog {
    public CustomDatePickerDialog(Context context, OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }

    @Override
    public void onClick(@NonNull DialogInterface dialog, int which) {
        super.onClick(dialog, which);
    }


}
