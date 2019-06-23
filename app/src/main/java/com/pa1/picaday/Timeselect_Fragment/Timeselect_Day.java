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
import android.widget.Toast;

import com.pa1.picaday.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Timeselect_Day extends Fragment {
    public Calendar start_cal = Calendar.getInstance();
    public Calendar end_cal = Calendar.getInstance();

    private TextView text_dp_start;
    private TextView text_dp_end;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MMM dd일 (E)", Locale.getDefault());

    private boolean MODE_EDIT = false;

    public void setMODE_EDIT(boolean MODE_EDIT) {
        this.MODE_EDIT = MODE_EDIT;
    }

    public Timeselect_Day() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timeselect_day, container, false);

        text_dp_start = (TextView) view.findViewById(R.id.startdate_day);
        text_dp_end = (TextView) view.findViewById(R.id.enddate_day);

        /* TIme 초기 설정 */
        /* EDIT Mode일 때 */
        if (MODE_EDIT) {
            text_dp_start.setText(sdf.format(start_cal.getTime()));
            text_dp_end.setText(sdf.format(end_cal.getTime()));
        }
        else {
            Calendar today_calendar = Calendar.getInstance();
            if (text_dp_start.getText() == "") {
                text_dp_start.setText(sdf.format(today_calendar.getTime()));
            }
            if (text_dp_end.getText() == "") {
                text_dp_end.setText(sdf.format(today_calendar.getTime()));
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
                        newCalendar.set(year, month, dayOfMonth, 0, 0, 0);

                        Calendar newendCalendar = Calendar.getInstance();
                        Date newendDate = new Date(end_cal.getTime().getTime() - start_cal.getTime().getTime() + newCalendar.getTime().getTime());
                        newendCalendar.setTime(newendDate);

                        start_cal.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                        text_dp_start.setText(sdf.format(newCalendar.getTime()));
                        end_cal.set(newendCalendar.get(Calendar.YEAR), newendCalendar.get(Calendar.MONTH), newendCalendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
                        text_dp_end.setText(sdf.format(newendCalendar.getTime()));
                        Toast.makeText(getActivity(), "일정 시작 시간을 설정했습니다", Toast.LENGTH_SHORT).show();
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
                        newCalendar.set(year, month, dayOfMonth);
                        Date newDate = newCalendar.getTime();

                        if (newDate.getTime() > start_cal.getTime().getTime()) {
                            text_dp_end.setText(sdf.format(newDate));
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
        return view;
    }
}
