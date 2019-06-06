package com.pa1.picaday.AddActivity_Fragment;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pa1.picaday.CustomTimePickerDialog;
import com.pa1.picaday.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActivity_daily extends BottomSheetDialogFragment {
    public static AddActivity_daily getInstance() { return new AddActivity_daily(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_daily, container, false);

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) view.findViewById(R.id.btn_check_day);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = getIntent();

                //startActivity(new Intent(getApplicationContext(), MainActivity_daily.class));
                dismiss();
            }
        });

        /* X 버튼 눌렀을 때 */
        ImageButton btn_cancel = (ImageButton) view.findViewById(R.id.btn_cancel_day);
        btn_cancel.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        /* TImePicker 구동 */

        final Calendar today_calendar = Calendar.getInstance();
        final Date today = today_calendar.getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat("MMM d일 (E) HH:mm", Locale.getDefault());

        final TextView text_tp_start = (TextView) view.findViewById(R.id.start_timepicker_day);
        if (text_tp_start.getText() == "") {
            text_tp_start.setText(sdf.format(today));
        }
        final TextView text_tp_end = (TextView) view.findViewById(R.id.end_timepicker_day);
        if (text_tp_end.getText() == "") {
            text_tp_end.setText(sdf.format(today));
        }


        final Calendar start_cal = Calendar.getInstance();
        final Calendar end_cal = Calendar.getInstance();
        text_tp_start.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTimePickerDialog tp_start = new CustomTimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(start_cal.get(Calendar.YEAR), start_cal.get(Calendar.MONTH), start_cal.get(Calendar.DATE), hourOfDay, minute);

                        Calendar newendCalendar = Calendar.getInstance();
                        Date newendDate = new Date(end_cal.getTime().getTime() - start_cal.getTime().getTime() + newCalendar.getTime().getTime());
                        newendCalendar.setTime(newendDate);

                        start_cal.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                        text_tp_start.setText(sdf.format(newCalendar.getTime()));
                        end_cal.set(newendCalendar.get(Calendar.YEAR), newendCalendar.get(Calendar.MONTH), newendCalendar.get(Calendar.DAY_OF_MONTH), newendCalendar.get(Calendar.HOUR_OF_DAY), newendCalendar.get(Calendar.MINUTE));
                        text_tp_end.setText(sdf.format(newendCalendar.getTime()));
                        Toast.makeText(AddActivity_daily.this.getContext(), "Start Time Set Completed", Toast.LENGTH_SHORT).show();
                    }
                }, start_cal.get(Calendar.HOUR_OF_DAY), start_cal.get(Calendar.MINUTE),false);
                tp_start.show();
            }
        });


        text_tp_end.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTimePickerDialog tp_end = new CustomTimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(end_cal.get(Calendar.YEAR), end_cal.get(Calendar.MONTH), start_cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                        Date newDate = newCalendar.getTime();

                        if (newDate.getTime() > start_cal.getTime().getTime()) {
                            text_tp_end.setText(sdf.format(newDate));
                            end_cal.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);

                            Toast.makeText(AddActivity_daily.this.getContext(), "End Time Set Completed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AddActivity_daily.this.getContext(), "End Time Set Failed", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, end_cal.get(Calendar.HOUR_OF_DAY), end_cal.get(Calendar.MINUTE),false);
                tp_end.show();

            }
        });



        return view;
    }

    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        }
    };
}
