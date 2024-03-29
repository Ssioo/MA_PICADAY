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
import android.widget.Toast;

import com.pa1.picaday.CustomUI.CustomTimePickerDialog;
import com.pa1.picaday.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Timeselect_Time extends Fragment {
    public Calendar start_cal = Calendar.getInstance();
    public Calendar end_cal = Calendar.getInstance();

    private TextView text_dp_start;
    private TextView text_tp_start;
    private TextView text_dp_end;
    private TextView text_tp_end;

    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy년 MMM dd일 (E)", Locale.getDefault());
    private SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private boolean MODE_EDIT = false;

    public void setMODE_EDIT(boolean MODE_EDIT) {
        this.MODE_EDIT = MODE_EDIT;
    }

    public Timeselect_Time() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timeselect_time, container, false);

        text_dp_start = (TextView) view.findViewById(R.id.startdate_time);
        text_tp_start = (TextView) view.findViewById(R.id.starttime_time);
        text_dp_end = (TextView) view.findViewById(R.id.enddate_time);
        text_tp_end = (TextView) view.findViewById(R.id.endtime_time);


        /* TIme 초기 설정 */
        /* Edit Mode일 때 */
        if (MODE_EDIT) {
            text_dp_start.setText(sdf1.format(start_cal.getTime()));
            text_dp_end.setText(sdf1.format(end_cal.getTime()));
            text_tp_start.setText(sdf2.format(start_cal.getTime()));
            text_tp_end.setText(sdf2.format(end_cal.getTime()));
        }
        else {
            Calendar today_calendar = Calendar.getInstance();
            if (text_dp_start.getText() == "") {
                text_dp_start.setText(sdf1.format(today_calendar.getTime()));
            }
            if (text_tp_start.getText() == "") {
                text_tp_start.setText(sdf2.format(today_calendar.getTime()));
            }
            if (text_dp_end.getText() == "") {
                text_dp_end.setText(sdf1.format(today_calendar.getTime()));
            }
            if (text_tp_end.getText() == "") {
                text_tp_end.setText(sdf2.format(today_calendar.getTime()));
            }
        }

        /* Time, DatePicker 조건부 */
        text_dp_start.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(year, month, dayOfMonth, start_cal.get(Calendar.HOUR_OF_DAY), start_cal.get(Calendar.MINUTE), 0);

                        Calendar newendCalendar = Calendar.getInstance();
                        Date newendDate = new Date(end_cal.getTime().getTime() - start_cal.getTime().getTime() + newCalendar.getTime().getTime());
                        newendCalendar.setTime(newendDate);

                        start_cal.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH), newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), 0);
                        text_dp_start.setText(sdf1.format(newCalendar.getTime()));
                        end_cal.set(newendCalendar.get(Calendar.YEAR), newendCalendar.get(Calendar.MONTH), newendCalendar.get(Calendar.DAY_OF_MONTH), newendCalendar.get(Calendar.HOUR_OF_DAY), newendCalendar.get(Calendar.MINUTE), 0);
                        text_dp_end.setText(sdf1.format(newendCalendar.getTime()));
                        Toast.makeText(getActivity(), "일정 시작 시간을 설정하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }, start_cal.get(Calendar.YEAR), start_cal.get(Calendar.MONTH), start_cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        text_dp_end.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(year, month, dayOfMonth, end_cal.get(Calendar.HOUR_OF_DAY), end_cal.get(Calendar.MINUTE), 0);
                        Date newDate = newCalendar.getTime();

                        if (newDate.getTime() > start_cal.getTime().getTime()) {
                            text_dp_end.setText(sdf1.format(newDate));
                            end_cal.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                            Toast.makeText(getActivity(), "일정 종료 시간을 설정하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "일정 종료 시간 설정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, end_cal.get(Calendar.YEAR), end_cal.get(Calendar.MONTH), end_cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        text_tp_start.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTimePickerDialog customTimePickerDialog = new CustomTimePickerDialog(getActivity(), new CustomTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(start_cal.get(Calendar.YEAR), start_cal.get(Calendar.MONTH), start_cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, 0);

                        Calendar newendCalendar = Calendar.getInstance();
                        Date newendDate = new Date(end_cal.getTime().getTime() - start_cal.getTime().getTime() + newCalendar.getTime().getTime());
                        newendCalendar.setTime(newendDate);

                        start_cal.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH), newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), 0);
                        text_tp_start.setText(sdf2.format(newCalendar.getTime()));
                        end_cal.set(newendCalendar.get(Calendar.YEAR), newendCalendar.get(Calendar.MONTH), newendCalendar.get(Calendar.DAY_OF_MONTH), newendCalendar.get(Calendar.HOUR_OF_DAY), newendCalendar.get(Calendar.MINUTE), 0);
                        text_dp_end.setText(sdf1.format(newendCalendar.getTime()));
                        text_tp_end.setText(sdf2.format(newendCalendar.getTime()));
                        Toast.makeText(getActivity(), "일정 시작 시간을 설정하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }, start_cal.get(Calendar.HOUR_OF_DAY),start_cal.get(Calendar.MINUTE),false);
                customTimePickerDialog.show();
            }
        });

        text_tp_end.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTimePickerDialog customTimePickerDialog = new CustomTimePickerDialog(getActivity(), new CustomTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(end_cal.get(Calendar.YEAR), end_cal.get(Calendar.MONTH), end_cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, 0);
                        Date newDate = newCalendar.getTime();

                        if (newDate.getTime() > start_cal.getTime().getTime()) {
                            text_tp_end.setText(sdf2.format(newDate));
                            end_cal.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH), newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), 0);

                            Toast.makeText(getActivity(), "일정 종료 시간을 설정하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "일정 종료 시간 설정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, end_cal.get(Calendar.HOUR_OF_DAY), end_cal.get(Calendar.MINUTE), false);
                customTimePickerDialog.show();
            }
        });
        return view;
    }
}
