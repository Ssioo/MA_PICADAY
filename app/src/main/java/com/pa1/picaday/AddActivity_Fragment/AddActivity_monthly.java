package com.pa1.picaday.AddActivity_Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pa1.picaday.R;

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

        /* TImePicker 구동 */

        final Calendar today_calendar = Calendar.getInstance();
        final Date today = today_calendar.getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일 HH:mm", Locale.getDefault());

        final TextView text_tp_start = (TextView) view.findViewById(R.id.starttime);
        if (text_tp_start.getText() == "") {
            text_tp_start.setText(sdf.format(today));
        }
        text_tp_start.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final TextView text_tp_end = (TextView) view.findViewById(R.id.endtime);
        if (text_tp_end.getText() == "") {
            text_tp_end.setText(sdf.format(today));
        }
        text_tp_end.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return view;
    }

}
