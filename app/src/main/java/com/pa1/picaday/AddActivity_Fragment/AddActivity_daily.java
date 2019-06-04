package com.pa1.picaday.AddActivity_Fragment;


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

import com.pa1.picaday.R;

import org.w3c.dom.Text;

import java.sql.Time;
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
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

        TextView tp_start = (TextView) view.findViewById(R.id.start_timepicker_day);
        tp_start.setText(sdf.format(today));
        tp_start.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView tp_end = (TextView) view.findViewById(R.id.end_timepicker_day);
        tp_end.setText(sdf.format(today));
        tp_end.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
