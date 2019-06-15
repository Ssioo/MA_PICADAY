package com.pa1.picaday.MainActivity_Fragment;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pa1.picaday.CustomUI.Dateinfo;
import com.pa1.picaday.CustomUI.DayCircleChart;
import com.pa1.picaday.CustomUI.DayCirclePin;
import com.pa1.picaday.Database.DBManager;
import com.pa1.picaday.R;
import com.pa1.picaday.CustomUI.WritingVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity_daily extends Fragment {

    public MainActivity_daily() {
        // Required empty public constructor
    }

    ArrayList<WritingVO> writing = new ArrayList<>();
    ArrayList<Dateinfo> today_list = new ArrayList<>();
    ArrayList<Pair<Date, Date>> datacal = new ArrayList<>();
    long today_left_time = 86400000;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.activity_main_daily, container, false);


        /* DB에 저장된 오늘 일정들을 표시,
        sp = start_cal의 hour, minute 부분의 long 값 (0~86400)
        ep = end-cal의 hour, minute 부분의 long 값 (0~86400)*/

        Canvas canvas = new Canvas();


        Calendar standardCal = Calendar.getInstance();
        standardCal.set(standardCal.get(Calendar.YEAR),standardCal.get(Calendar.MONTH),standardCal.get(Calendar.DAY_OF_MONTH), 0,0,0);
        long standcal = standardCal.getTime().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


        DBManager manager = new DBManager(getActivity());
        today_list = manager.selectAll_today(sdf.format(standardCal.getTime()));
        for (int i=0;i<today_list.size();i++) {
            try {
                datacal.add(new Pair<>(sdf_full.parse(today_list.get(i).getStart_time()),
                        sdf_full.parse(today_list.get(i).getEnd_time())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        for(int i=0; i<datacal.size(); i++) {
            writing.add(new WritingVO((datacal.get(i).first.getTime() - standcal)/1000, (datacal.get(i).second.getTime() - standcal)/1000, today_list.get(i).getTitle()));
            today_left_time = today_left_time - (datacal.get(i).second.getTime() - datacal.get(i).first.getTime());
        }

        FrameLayout daychart = view.findViewById(R.id.daychart);
        DayCircleChart dayCircleChart = new DayCircleChart(getActivity(), writing, 100, 1000);
        DayCirclePin dayCirclePin = new DayCirclePin(getActivity(), 100, 1000);
        daychart.addView(dayCircleChart);
        daychart.addView(dayCirclePin);



        /* Text style 세팅 */
        SharedPreferences sd = getActivity().getSharedPreferences("style_settings", 0);

        TextView lefttime = view.findViewById(R.id.time_left_today);
        if (sd.getBoolean("style_timer1", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.american_captain));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.warm_blue));
        }
        else if (sd.getBoolean("style_timer2", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.baemin_jua));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.coral_red));
        }
        Date today_left = new Date(today_left_time + standcal);
        SimpleDateFormat tempSDF = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        lefttime.setText(tempSDF.format(today_left));

        return view;
    }

    /*@Override
    public void onResume() {
        super.onResume();
        datacal.clear();
        today_list.clear();
        writing.clear();

        Calendar standardCal = Calendar.getInstance();
        standardCal.set(standardCal.get(Calendar.YEAR),standardCal.get(Calendar.MONTH),standardCal.get(Calendar.DAY_OF_MONTH), 0,0,0);
        long standcal = standardCal.getTime().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


        DBManager manager = new DBManager(getActivity());
        today_list = manager.selectAll_today(sdf.format(standardCal.getTime()));
        for (int i=0;i<today_list.size();i++) {
            try {
                datacal.add(new Pair<>(sdf_full.parse(today_list.get(i).getStart_time()),
                        sdf_full.parse(today_list.get(i).getEnd_time())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        for(int i=0; i<datacal.size(); i++) {
            writing.add(new WritingVO((datacal.get(i).first.getTime() - standcal)/1000, (datacal.get(i).second.getTime() - standcal)/1000));
            today_left_time = today_left_time - (datacal.get(i).second.getTime() - datacal.get(i).first.getTime());
        }

        *//*FrameLayout daychart = view.findViewById(R.id.daychart);
        DayCircleChart dayCircleChart = new DayCircleChart(getActivity(), writing, 100, 1000);
        DayCirclePin dayCirclePin = new DayCirclePin(getActivity(), 100, 1000);
        daychart.addView(dayCircleChart);
        daychart.addView(dayCirclePin);*//*
    }*/
}
