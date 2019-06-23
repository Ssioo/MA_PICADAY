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

import com.pa1.picaday.CustomUI.CustomTimePickerDialog;
import com.pa1.picaday.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Timeselect_Deadline extends Fragment {
    public Calendar end_cal;

    private TextView text_dp_end;
    private TextView text_tp_end;

    //final SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MMM dd일 (E) HH:mm", Locale.getDefault());
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy년 MMM dd일 (E)", Locale.getDefault());
    private SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private boolean MODE_EDIT = false;

    public void setMODE_EDIT(boolean MODE_EDIT) { this.MODE_EDIT = MODE_EDIT; }

    public Timeselect_Deadline() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timeselect_deadline, container, false);

        text_dp_end = (TextView) view.findViewById(R.id.enddate_deadline);
        text_tp_end = (TextView) view.findViewById(R.id.endtime_deadline);

        end_cal = Calendar.getInstance();

        /* TImePicker 초기 시간 표시하기 */
        /* Edit Mode일 때 */
        if (MODE_EDIT) {
            text_dp_end.setText(sdf1.format(end_cal.getTime()));
            text_tp_end.setText(sdf2.format(end_cal.getTime()));
        }
        else {
            final Calendar today_calendar = Calendar.getInstance();
            if (text_dp_end.getText() == "") {
                text_dp_end.setText(sdf1.format(today_calendar.getTime()));
            }
            if (text_tp_end.getText() == "") {
                text_tp_end.setText(sdf2.format(today_calendar.getTime()));
            }
        }

        /* Time, DatePicker 조건부 */
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
                        end_cal.set(end_cal.get(Calendar.YEAR), end_cal.get(Calendar.MONTH), end_cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, 0);
                        text_tp_end.setText(sdf2.format(end_cal.getTime()));
                    }
                }, end_cal.get(Calendar.HOUR_OF_DAY), end_cal.get(Calendar.MINUTE), false);
                customTimePickerDialog.show();
            }
        });

        return view;
    }
}
