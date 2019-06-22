package com.pa1.picaday.CustomUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.pa1.picaday.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomDialogFragment_Daylist extends DialogFragment {
    private int width;
    private int height;
    public CustomDialogFragment_Daylist() {
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    ArrayList<Dateinfo> today_list = new ArrayList<>();
    long today_left_time = 86400000;
    Date today;

    public void setToday_list(ArrayList<Dateinfo> today_list) {
        this.today_list = today_list;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();

        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_daily_list, container, false);


        /* 오늘 일정 리스트뷰 작성 */
        ListView todaylist = view.findViewById(R.id.daylist);
        CustomdaylistAdapter customdaylistAdapter = new CustomdaylistAdapter();
        customdaylistAdapter.addList(today_list);
        todaylist.setAdapter(customdaylistAdapter);
        Log.e("todaylist", String.valueOf(today_list.size()));
        //Log.e("todaylist", today_list.get(0).getTitle());

        /* Text style 세팅 */
        SharedPreferences sd = getActivity().getSharedPreferences("style_settings", 0);

        TextView lefttime = view.findViewById(R.id.time_left_today_list);
        if (sd.getBoolean("style_timer1", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.american_captain));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.warm_blue));
        }
        else if (sd.getBoolean("style_timer2", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.baemin_jua));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.coral_red));
        }
        Calendar standcal = Calendar.getInstance();
        standcal.setTime(today);
        standcal.set(Calendar.HOUR_OF_DAY, 0);
        standcal.set(Calendar.MINUTE, 0);
        standcal.set(Calendar.SECOND, 0);
        Date today_left = new Date(today_left_time + standcal.getTime().getTime());
        SimpleDateFormat tempSDF = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        lefttime.setText(tempSDF.format(today_left));

        /* Dialog title이랑 background 설정 */



        return view;
    }
}
