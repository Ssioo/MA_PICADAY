package com.pa1.picaday.Timeselect_Fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.pa1.picaday.CustomTimePickerDialog;
import com.pa1.picaday.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Timeselect_Deadline extends Fragment {
    private Calendar end_cal;

    public Timeselect_Deadline() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timeselect_deadline, container, false);

        /* TImePicker 구동 */
        final Calendar today_calendar = Calendar.getInstance();
        final Date today = today_calendar.getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MMM dd일 (E) HH:mm", Locale.getDefault());
        final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy년 MMM dd일 (E)", Locale.getDefault());
        final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

        final TextView text_dp_end = (TextView) view.findViewById(R.id.enddate_deadline);
        if (text_dp_end.getText() == "") {
            text_dp_end.setText(sdf1.format(today));
        }
        final TextView text_tp_end = (TextView) view.findViewById(R.id.endtime_deadline);
        if (text_tp_end.getText() == "") {
            text_tp_end.setText(sdf2.format(today));
        }

        end_cal = Calendar.getInstance();
        text_dp_end.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        end_cal.set(year, month, dayOfMonth);
                        text_dp_end.setText(sdf1.format(end_cal.getTime()));
                    }
                }, end_cal.get(Calendar.YEAR), end_cal.get(Calendar.MONTH), end_cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        text_tp_end.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTimePickerDialog customTimePickerDialog = new CustomTimePickerDialog(getActivity(), new CustomTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        end_cal.set(end_cal.get(Calendar.YEAR), end_cal.get(Calendar.MONTH), end_cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                        text_tp_end.setText(sdf2.format(end_cal.getTime()));
                    }
                }, end_cal.get(Calendar.HOUR_OF_DAY), end_cal.get(Calendar.MINUTE), false);
                customTimePickerDialog.show();
            }
        });

        return view;
    }

    public void getCalDeadline(Calendar end_cal){
        end_cal = this.end_cal;
    }
}
