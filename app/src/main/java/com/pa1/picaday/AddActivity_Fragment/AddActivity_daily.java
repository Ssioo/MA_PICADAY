package com.pa1.picaday.AddActivity_Fragment;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pa1.picaday.CustomTimePickerDialog;
import com.pa1.picaday.R;

import java.text.ParseException;
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

        /* Start, End Time TextView Initialization */
        final Calendar today_calendar = Calendar.getInstance(); // 오늘 시간 불러오기
        final Date today = today_calendar.getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault()); // Date Format 선언 : HH:mm

        final TextView text_tp_start = (TextView) view.findViewById(R.id.start_timepicker_day);
        final TextView text_tp_end = (TextView) view.findViewById(R.id.end_timepicker_day);
        if (text_tp_start.getText() == "") {
            text_tp_start.setText(sdf.format(today));
        }
        if (text_tp_end.getText() == "") {
            text_tp_end.setText(sdf.format(today));
        }

        /* TimePicker 호출 */
        try {
            /* TextView로부터 기존의 시간 불러오기, 기존 시간 정보를 초기 Timepicker 시간으로 설정 */
            final Calendar start_cal = Calendar.getInstance();
            start_cal.setTime(sdf.parse(text_tp_start.getText().toString()));
            start_cal.set(today_calendar.get(Calendar.YEAR), today_calendar.get(Calendar.MONTH), today_calendar.get(Calendar.DAY_OF_MONTH));
            final Calendar end_cal = Calendar.getInstance();
            end_cal.setTime(sdf.parse(text_tp_end.getText().toString()));
            end_cal.set(today_calendar.get(Calendar.YEAR), today_calendar.get(Calendar.MONTH), today_calendar.get(Calendar.DAY_OF_MONTH));


            /* Starttime TimePicker 액션 */
            text_tp_start.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomTimePickerDialog tp_start = new CustomTimePickerDialog(AddActivity_daily.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Calendar newCalendar = Calendar.getInstance();
                            newCalendar.set(start_cal.get(Calendar.YEAR), start_cal.get(Calendar.MONTH), start_cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);

                            Date newendDate = new Date((end_cal.getTime().getTime() - start_cal.getTime().getTime()) + newCalendar.getTime().getTime());
                            start_cal.setTime(newCalendar.getTime());
                            end_cal.setTime(newendDate);
                            text_tp_start.setText(sdf.format(newCalendar.getTime()));
                            text_tp_end.setText(sdf.format(newendDate));

                            Toast.makeText(AddActivity_daily.this.getContext(),"Start Time Set Completed", Toast.LENGTH_SHORT).show();
                        }

                    }, start_cal.get(Calendar.HOUR_OF_DAY), start_cal.get(Calendar.MINUTE), false);
                    tp_start.show();
                }
            });

            /* EndTime TimePicker 액션 */
            text_tp_end.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomTimePickerDialog tp_end = new CustomTimePickerDialog(AddActivity_daily.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Calendar newCalendar = Calendar.getInstance();
                            newCalendar.set(end_cal.get(Calendar.YEAR), end_cal.get(Calendar.MONTH), end_cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);

                            long calDate = newCalendar.getTime().getTime() - start_cal.getTime().getTime();
                            if(calDate > 0) {
                                end_cal.set(end_cal.get(Calendar.YEAR), end_cal.get(Calendar.MONTH), end_cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                                text_tp_end.setText(sdf.format(end_cal.getTime()));
                                Toast.makeText(AddActivity_daily.this.getContext(), "End Time Set Completed", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(AddActivity_daily.this.getContext(), "Input the right End time", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, end_cal.get(Calendar.HOUR_OF_DAY), end_cal.get(Calendar.MINUTE),false);
                    tp_end.show();
                }
            });



        } catch (ParseException e) {
            e.printStackTrace();
        }



        return view;
    }
}
