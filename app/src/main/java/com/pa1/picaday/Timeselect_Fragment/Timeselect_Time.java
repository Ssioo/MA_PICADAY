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

import com.pa1.picaday.CustomTimePickerDialog;
import com.pa1.picaday.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Timeselect_Time extends Fragment {
    private Calendar start_cal = Calendar.getInstance();
    private Calendar end_cal = Calendar.getInstance();

    public Timeselect_Time() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timeselect_time, container, false);

        /* TImePicker 구동 */

        final Calendar today_calendar = Calendar.getInstance();
        final Date today = today_calendar.getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MMM dd일 (E) HH:mm", Locale.getDefault());
        final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy년 MMM dd일 (E)", Locale.getDefault());
        final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

        final TextView text_dp_start = (TextView) view.findViewById(R.id.startdate_time);
        if (text_dp_start.getText() == "") {
            text_dp_start.setText(sdf1.format(today));
        }
        final TextView text_tp_start = (TextView) view.findViewById(R.id.starttime_time);
        if (text_tp_start.getText() == "") {
            text_tp_start.setText(sdf2.format(today));
        }
        final TextView text_dp_end = (TextView) view.findViewById(R.id.enddate_time);
        if (text_dp_end.getText() == "") {
            text_dp_end.setText(sdf1.format(today));
        }
        final TextView text_tp_end = (TextView) view.findViewById(R.id.endtime_time);
        if (text_tp_end.getText() == "") {
            text_tp_end.setText(sdf2.format(today));
        }

        text_dp_start.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(year, month, dayOfMonth, start_cal.get(Calendar.HOUR_OF_DAY), start_cal.get(Calendar.MINUTE));

                        Calendar newendCalendar = Calendar.getInstance();
                        Date newendDate = new Date(end_cal.getTime().getTime() - start_cal.getTime().getTime() + newCalendar.getTime().getTime());
                        newendCalendar.setTime(newendDate);

                        start_cal.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH), newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE));
                        text_dp_start.setText(sdf1.format(newCalendar.getTime()));
                        end_cal.set(newendCalendar.get(Calendar.YEAR), newendCalendar.get(Calendar.MONTH), newendCalendar.get(Calendar.DAY_OF_MONTH), newendCalendar.get(Calendar.HOUR_OF_DAY), newendCalendar.get(Calendar.MINUTE));
                        text_dp_end.setText(sdf1.format(newendCalendar.getTime()));
                        Toast.makeText(getActivity(), "Start Time Set Completed", Toast.LENGTH_SHORT).show();
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
                        newCalendar.set(year, month, dayOfMonth, end_cal.get(Calendar.HOUR_OF_DAY), end_cal.get(Calendar.MINUTE));
                        Date newDate = newCalendar.getTime();

                        if (newDate.getTime() > start_cal.getTime().getTime()) {
                            text_dp_end.setText(sdf1.format(newDate));
                            end_cal.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                            Toast.makeText(getActivity(), "End Time Set Completed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "End Time Set Failed", Toast.LENGTH_SHORT).show();
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
                        newCalendar.set(start_cal.get(Calendar.YEAR), start_cal.get(Calendar.MONTH), start_cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);

                        Calendar newendCalendar = Calendar.getInstance();
                        Date newendDate = new Date(end_cal.getTime().getTime() - start_cal.getTime().getTime() + newCalendar.getTime().getTime());
                        newendCalendar.setTime(newendDate);

                        start_cal.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH), newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE));
                        text_tp_start.setText(sdf2.format(newCalendar.getTime()));
                        end_cal.set(newendCalendar.get(Calendar.YEAR), newendCalendar.get(Calendar.MONTH), newendCalendar.get(Calendar.DAY_OF_MONTH), newendCalendar.get(Calendar.HOUR_OF_DAY), newendCalendar.get(Calendar.MINUTE));
                        text_dp_end.setText(sdf1.format(newendCalendar.getTime()));
                        text_tp_end.setText(sdf2.format(newendCalendar.getTime()));
                        Toast.makeText(getActivity(), "Start Time Set Completed", Toast.LENGTH_SHORT).show();
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
                        newCalendar.set(start_cal.get(Calendar.YEAR), start_cal.get(Calendar.MONTH), start_cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                        Date newDate = newCalendar.getTime();

                        if (newDate.getTime() > start_cal.getTime().getTime()) {
                            text_tp_end.setText(sdf2.format(newDate));
                            end_cal.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH), newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE));

                            Toast.makeText(getActivity(), "End Time Set Completed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "End Time Set Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, end_cal.get(Calendar.HOUR_OF_DAY), end_cal.get(Calendar.MINUTE), false);
                customTimePickerDialog.show();
            }
        });
        return view;
    }

    public void getCalTime(Calendar start_cal, Calendar end_cal){
        start_cal.setTime(this.start_cal.getTime());
        end_cal.setTime(this.end_cal.getTime());
    }
}
