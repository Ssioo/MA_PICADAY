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
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

        final TextView text_tp_start = (TextView) view.findViewById(R.id.start_timepicker_day);
        if (text_tp_start.getText() == "") {
            text_tp_start.setText(sdf.format(today));
        }
        text_tp_start.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTimePickerDialog tp_start = new CustomTimePickerDialog(AddActivity_daily.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(today_calendar.get(Calendar.YEAR),
                                today_calendar.get(Calendar.MONTH),
                                today_calendar.get(Calendar.DATE),
                                hourOfDay,
                                minute,
                                newCalendar.get(Calendar.SECOND));
                        Date newDate = newCalendar.getTime();
                        text_tp_start.setText(sdf.format(newDate));

                        Toast.makeText(AddActivity_daily.this.getContext(),
                                "Start Time Set Completed",
                                Toast.LENGTH_SHORT).show();
                    }
                }, today_calendar.get(Calendar.HOUR_OF_DAY), today_calendar.get(Calendar.MINUTE),false);
                tp_start.show();
            }
        });

        final TextView text_tp_end = (TextView) view.findViewById(R.id.end_timepicker_day);
        if (text_tp_end.getText() == "") {
            text_tp_end.setText(sdf.format(today));
        }
        text_tp_end.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTimePickerDialog tp_end = new CustomTimePickerDialog(AddActivity_daily.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(today_calendar.get(Calendar.YEAR),
                                today_calendar.get(Calendar.MONTH),
                                today_calendar.get(Calendar.DATE),
                                hourOfDay,
                                minute,
                                newCalendar.get(Calendar.SECOND));
                        Date newDate = newCalendar.getTime();
                        text_tp_end.setText(sdf.format(newDate));

                        Toast.makeText(AddActivity_daily.this.getContext(),
                                "Start Time Set Completed",
                                Toast.LENGTH_SHORT).show();
                    }
                }, today_calendar.get(Calendar.HOUR_OF_DAY), today_calendar.get(Calendar.MINUTE),false);
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
