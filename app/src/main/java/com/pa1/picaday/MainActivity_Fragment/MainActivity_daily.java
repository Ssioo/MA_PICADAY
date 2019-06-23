package com.pa1.picaday.MainActivity_Fragment;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pa1.picaday.CustomUI.Dateinfo;
import com.pa1.picaday.CustomUI.DayCircleChart;
import com.pa1.picaday.CustomUI.DayCirclePin;
import com.pa1.picaday.CustomUI.WritingVO;
import com.pa1.picaday.Database.DBManager;
import com.pa1.picaday.R;

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
    private TextView lefttime;
    private FrameLayout daychart;
    private DayCircleChart dayCircleChart;
    private DayCirclePin dayCirclePin;
    Point size;
    Handler handler;
    long today_left_time;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    SimpleDateFormat tempSDF = new SimpleDateFormat("H시간 m분 s초", Locale.getDefault());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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
        final long standcal = standardCal.getTime().getTime();

        DBManager manager = new DBManager(getActivity());
        today_list = manager.selectAll_today(sdf.format(standardCal.getTime()));
        for (int i=0;i<today_list.size();i++) {
            long datacal_sp = 0;
            long datacal_ep = 0;
            try {
                datacal_sp = sdf_full.parse(today_list.get(i).getStart_time()).getTime();
                datacal_ep = sdf_full.parse(today_list.get(i).getEnd_time()).getTime();
                writing.add(new WritingVO((datacal_sp - standcal)/1000, (datacal_ep - standcal)/1000, today_list.get(i).getTitle()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        Log.e("width", String.valueOf(size.x));
        Log.e("height", String.valueOf(size.y));
        daychart = view.findViewById(R.id.daychart);
        dayCircleChart = new DayCircleChart(getActivity(), writing, size.x);
        dayCirclePin = new DayCirclePin(getActivity(), size.x);
        daychart.addView(dayCircleChart, 0);
        daychart.addView(dayCirclePin, 1);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                get_lefttime(standcal);
                daychart.removeViewAt(1);
                daychart.addView(dayCirclePin, 1);
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        /* Text style 세팅 */
        SharedPreferences sd = getActivity().getSharedPreferences("style_settings", 0);

        lefttime = view.findViewById(R.id.time_left_today);
        if (sd.getBoolean("style_timer1", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.american_captain));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.warm_blue));
        }
        else if (sd.getBoolean("style_timer2", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.baemin_jua));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.coral_red));
        }


        return view;
    }



    private void get_lefttime(long standcal) {
        Calendar nowcal = Calendar.getInstance();
        Calendar tommorowcal = Calendar.getInstance();
        tommorowcal.add(Calendar.DAY_OF_MONTH, 1);
        tommorowcal.set(Calendar.HOUR_OF_DAY, 0);
        tommorowcal.set(Calendar.MINUTE, 0);
        tommorowcal.set(Calendar.SECOND, 0);
        today_left_time = tommorowcal.getTime().getTime() - nowcal.getTime().getTime();
        for(int i=0; i<today_list.size(); i++) {
            long nowcallong = nowcal.getTime().getTime();
            long datacal_sp = 0;
            long datacal_ep = 0;
            try {
                datacal_sp = sdf_full.parse(today_list.get(i).getStart_time()).getTime();
                datacal_ep = sdf_full.parse(today_list.get(i).getEnd_time()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (nowcallong > datacal_ep) {
                continue; // endtime보다 지났을 때 : 안 빼기
            }
            else if (nowcallong <= datacal_ep && nowcallong > datacal_sp) {
                // 현재 starttime이랑 endtime 사이에 있을 때
                today_left_time = today_left_time - (datacal_ep - nowcal.getTime().getTime());
            }
            else {
                // starttime 전일 때 : 다 빼기
                today_left_time = today_left_time - (datacal_ep - datacal_sp);
            }

        }
        Date today_left = new Date(today_left_time + tommorowcal.getTime().getTime());
        lefttime.setText(tempSDF.format(today_left));
    }
}
