package com.pa1.picaday.AddActivity_Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pa1.picaday.CustomTimePickerDialog;
import com.pa1.picaday.R;

import java.net.SocketImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddActivity_monthly extends BottomSheetDialogFragment {

    public static AddActivity_monthly getInstance() { return new AddActivity_monthly();    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_monthly, container, false);

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) view.findViewById(R.id.btn_check_month);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = getIntent();

                //startActivity(new Intent(getApplicationContext(), MainActivity_monthly.class));
                dismiss();
            }
        });

        /* X 버튼 눌렀을 때 */
        ImageButton btn_cancel = (ImageButton) view.findViewById(R.id.btn_cancel_month);
        btn_cancel.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        /* 시간, 하루종일, 데드라인 형태 변환 체크 */
        final CheckBox chk_time = (CheckBox) view.findViewById(R.id.chk_time);
        final CheckBox chk_day = (CheckBox) view.findViewById(R.id.chk_day);
        final CheckBox chk_deadline = (CheckBox) view.findViewById(R.id.chk_deadline);
        chk_time.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                chk_time.setChecked(true);
                chk_day.setChecked(false);
                chk_deadline.setChecked(false);
            }
        });
        chk_day.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                chk_time.setChecked(false);
                chk_day.setChecked(true);
                chk_deadline.setChecked(false);
            }
        });
        chk_deadline.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                chk_day.setChecked(false);
                chk_time.setChecked(false);
                chk_deadline.setChecked(true);
            }
        });

        /* DatePicker & TimePicker 구동 */
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MMM d일 HH:mm", Locale.getDefault());
        final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy년 MMM d일", Locale.getDefault()); // DatePicker용
        final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault()); // TImePicker용

        final Calendar today_calendar = Calendar.getInstance();
        final Date today = today_calendar.getTime();

        final TextView text_dp_start = (TextView) view.findViewById(R.id.startdate);
        if (text_dp_start.getText() == "") {
            text_dp_start.setText(sdf1.format(today));
        }
        final TextView text_dp_end = (TextView) view.findViewById(R.id.enddate);
        if (text_dp_end.getText() == "") {
            text_dp_end.setText(sdf1.format(today));
        }
        final TextView text_tp_start = (TextView) view.findViewById(R.id.starttime);
        if (text_tp_start.getText() == "") {
            text_tp_start.setText(sdf2.format(today));
        }
        final TextView text_tp_end = (TextView) view.findViewById(R.id.endtime);
        if (text_tp_end.getText() == "") {
            text_tp_end.setText(sdf2.format(today));
        }

        /* DatePicker 호출 */
        try {
            final Calendar start_cal = Calendar.getInstance();
            final Calendar end_cal = Calendar.getInstance();
            start_cal.setTime(sdf.parse(text_dp_start.getText().toString() + " " + text_tp_start.getText().toString()));
            end_cal.setTime(sdf.parse(text_dp_end.getText().toString() + " " + text_tp_end.getText().toString()));


            /*시작 Date 설정*/
            text_dp_start.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePicker = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            start_cal.set(year, month, dayOfMonth);
                            text_dp_start.setText(sdf1.format(start_cal.getTime()));
                            Toast.makeText(getActivity(), "Start Date Selected", Toast.LENGTH_SHORT).show();
                        }
                    }, start_cal.get(Calendar.YEAR), start_cal.get(Calendar.MONTH), start_cal.get(Calendar.DAY_OF_MONTH));
                    datePicker.show();

                }
            });

            /* 종료 Date 설정 */
            text_dp_end.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePicker = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            end_cal.set(year, month, dayOfMonth);
                            text_dp_end.setText(sdf1.format(end_cal.getTime()));
                            Toast.makeText(getActivity(), "End Date Selected", Toast.LENGTH_SHORT).show();

                        }
                    }, end_cal.get(Calendar.YEAR), end_cal.get(Calendar.MONTH), end_cal.get(Calendar.DAY_OF_MONTH));
                    datePicker.show();
                }
            });

            /* 시작 Time 설정 */
            text_tp_start.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomTimePickerDialog customTimePickerDialog = new CustomTimePickerDialog(getActivity(), new CustomTimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            start_cal.set(start_cal.get(Calendar.YEAR), start_cal.get(Calendar.MONTH), start_cal.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                            text_tp_start.setText(sdf2.format(start_cal.getTime()));
                        }
                    }, start_cal.get(Calendar.HOUR_OF_DAY), start_cal.get(Calendar.MINUTE), false);

                    customTimePickerDialog.show();
                }
            });

            /* 종료 Time 설정 */
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

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

}
