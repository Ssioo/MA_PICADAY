package com.pa1.picaday.MainActivity_Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pa1.picaday.CustomUI.Dateinfo;
import com.pa1.picaday.Database.DBManager;
import com.pa1.picaday.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity_weekly extends Fragment {

    long thisweek_left_time;
    long standcal;
    Calendar calendar;
    private TextView lefttime;
    private TextView weekview_header;
    private TextView weekview_dayofmon;
    private TextView weekview_dayoftue;
    private TextView weekview_dayofwed;
    private TextView weekview_dayofthr;
    private TextView weekview_dayoffri;
    private TextView weekview_dayofsat;
    private TextView weekview_dayofsun;
    RelativeLayout monday;
    RelativeLayout tuesday;
    RelativeLayout wednesday;
    RelativeLayout thursday;
    RelativeLayout friday;
    RelativeLayout saturday;
    RelativeLayout sunday;
    ArrayList<RelativeLayout> days = new ArrayList<>();
    ArrayList<String> textArr = new ArrayList<>();
    ArrayList<Dateinfo> thisweek_list = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    SimpleDateFormat tempSDF = new SimpleDateFormat("d일 H시간 m분", Locale.getDefault());
    SimpleDateFormat tempSDF_d = new SimpleDateFormat("d", Locale.getDefault());
    Handler handler;
    Fragment act = this;

    public MainActivity_weekly() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_main_weekly, container, false);

        weekview_dayofmon = view.findViewById(R.id.weekview_day_ofmon);
        weekview_dayoftue = view.findViewById(R.id.weekview_day_oftue);
        weekview_dayofwed = view.findViewById(R.id.weekview_day_ofwed);
        weekview_dayofthr = view.findViewById(R.id.weekview_day_ofthr);
        weekview_dayoffri = view.findViewById(R.id.weekview_day_offri);
        weekview_dayofsat = view.findViewById(R.id.weekview_day_ofsat);
        weekview_dayofsun = view.findViewById(R.id.weekview_day_ofsun);

        monday = view.findViewById(R.id.week_list_monday);
        tuesday = view.findViewById(R.id.week_list_tuesday);
        wednesday = view.findViewById(R.id.week_list_wednesday);
        thursday = view.findViewById(R.id.week_list_thursday);
        friday = view.findViewById(R.id.week_list_friday);
        saturday = view.findViewById(R.id.week_list_saturday);
        sunday = view.findViewById(R.id.week_list_sunday);
        days.add(monday);
        days.add(tuesday);
        days.add(wednesday);
        days.add(thursday);
        days.add(friday);
        days.add(saturday);
        days.add(sunday);

        /* Text style 세팅 */
        SharedPreferences sdp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        lefttime = view.findViewById(R.id.time_left_thisweek);
        if (sdp.getString("timerstyle", "").equals("스타일1")) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.american_captain));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.warm_blue));
        }
        else if (sdp.getString("timerstyle", "").equals("스타일2")) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.baemin_jua));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.coral_red));
        }

        Calendar standardCal_start = Calendar.getInstance();
        Calendar standardCal_end = Calendar.getInstance();
        if (standardCal_start.get(Calendar.DAY_OF_WEEK) == 1) {
            // 일요일일 경우
            standardCal_start.add(Calendar.DAY_OF_MONTH, -6); // 월요일
            standardCal_end.add(Calendar.DAY_OF_MONTH, 1); // 다음주 월요일 0시 0분 0초
        }
        else {
            // 그외
            standardCal_start.add(Calendar.DAY_OF_MONTH, 2 - standardCal_start.get(Calendar.DAY_OF_WEEK)); // 월요일
            standardCal_end.add(Calendar.DAY_OF_MONTH, 9 - standardCal_start.get(Calendar.DAY_OF_WEEK)); // 다음주 월요일 0시 0분 0초
        }
        standardCal_start.set(Calendar.HOUR_OF_DAY, 0);
        standardCal_start.set(Calendar.MINUTE, 0);
        standardCal_start.set(Calendar.SECOND, 0);
        standardCal_end.set(Calendar.HOUR_OF_DAY, 0);
        standardCal_end.set(Calendar.MINUTE, 0);
        standardCal_end.set(Calendar.SECOND, 0);

        standcal = standardCal_end.getTime().getTime();

        DBManager manager = new DBManager(getActivity());
        thisweek_list = manager.selectAll_thisweek(sdf.format(standardCal_start.getTime()), sdf.format(standardCal_end.getTime()));


        /* 달력 Header 꾸미기*/
        calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1)
            calendar.add(Calendar.DATE, -7);
        calendar.set(Calendar.DAY_OF_WEEK, 2);

        weekview_header = view.findViewById(R.id.weekview_header);
        weekview_header.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1) + "월 " + String.valueOf(calendar.get(Calendar.WEEK_OF_MONTH) + "째주"));
        ImageButton calendar_prev = view.findViewById(R.id.btn_week_prev);
        calendar_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DATE, -7);
                getweekCalendar(calendar.getTime(), view);
            }
        });
        ImageButton calendar_next = view.findViewById(R.id.btn_week_next);
        calendar_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DATE, 7);
                getweekCalendar(calendar.getTime(), view);
            }
        });

        /* 달력 이번주 월~일 날짜 채우기 */
        getweekCalendar(calendar.getTime(), view);

        /* 이번 주 남은 시간 계산 */
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                get_lefttime(standcal);
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


        return view;
    }

    private void get_lefttime(long nextweek_day) {
        Calendar nowcal = Calendar.getInstance();
        long nowcallong = nowcal.getTime().getTime();
        Calendar thismonth = Calendar.getInstance();
        thismonth.set(Calendar.DAY_OF_MONTH, 1);
        thismonth.set(Calendar.HOUR_OF_DAY, 0);
        thismonth.set(Calendar.MINUTE, 0);
        thismonth.set(Calendar.SECOND, 0);
        thisweek_left_time = nextweek_day - nowcal.getTime().getTime();
        for(int i=0; i<thisweek_list.size(); i++) {

            long datacal_sp = 0;
            long datacal_ep = 0;
            try {
                datacal_sp = sdf_full.parse(thisweek_list.get(i).getStart_time()).getTime();
                datacal_ep = sdf_full.parse(thisweek_list.get(i).getEnd_time()).getTime();
                if (nowcallong > datacal_ep) {
                    continue; // endtime보다 지났을 때 : 안 빼기
                }
                else if (nowcallong > datacal_sp) {
                    thisweek_left_time = thisweek_left_time - (datacal_ep - nowcal.getTime().getTime());
                    // 현재 starttime이랑 endtime 사이에 있을 때
                }
                else {
                    // starttime 전일 때 : 다 빼기
                    thisweek_left_time = thisweek_left_time - (datacal_ep - datacal_sp);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Date thisweek_left = new Date(thisweek_left_time + thismonth.getTime().getTime());
        String tempstr = String.valueOf(Integer.parseInt(tempSDF_d.format(thisweek_left)) - 1) + tempSDF.format(thisweek_left).substring(tempSDF.format(thisweek_left).indexOf("일"));
        lefttime.setText(tempstr);
    }

    private void getweekCalendar(Date date, View view) {
        textArr.clear();

        Calendar newcalendar = Calendar.getInstance();
        newcalendar.setTime(date);
        weekview_header.setText(String.valueOf(newcalendar.get(Calendar.MONTH) + 1) + "월 " + String.valueOf(newcalendar.get(Calendar.WEEK_OF_MONTH)) + "째주");

        for (int i=0; i< 7; i++) {
            textArr.add(String.valueOf(newcalendar.get(Calendar.DAY_OF_MONTH)));
            newcalendar.add(Calendar.DATE, 1);
        }
        /* 이번주 일자 작성 */
        weekview_dayofmon.setText(textArr.get(0));
        weekview_dayoftue.setText(textArr.get(1));
        weekview_dayofwed.setText(textArr.get(2));
        weekview_dayofthr.setText(textArr.get(3));
        weekview_dayoffri.setText(textArr.get(4));
        weekview_dayofsat.setText(textArr.get(5));
        weekview_dayofsun.setText(textArr.get(6));

        /* 달력 리스트뷰 RelativeLayout 호출 */
        ArrayList<TextView> tvset = new ArrayList<>();
        int[] howmuchon_day = {0, 0, 0, 0, 0, 0, 0};
        for (int i=0; i<thisweek_list.size(); i++) {
            Calendar tempcalendar = Calendar.getInstance();
            try {
                tempcalendar.setTime(sdf_full.parse(thisweek_list.get(i).getStart_time()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int day = (tempcalendar.get(Calendar.DAY_OF_WEEK) + 5) % 7;
            TextView tv = new TextView(getActivity());
            tv.setText(thisweek_list.get(i).getTitle());
            tv.setTextColor(getResources().getColor(R.color.white));
            tv.setBackgroundColor(getResources().getColor(R.color.coral_red));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setId(tv.generateViewId());
            if (howmuchon_day[day] != 0) {
                layoutParams.addRule(RelativeLayout.BELOW, days.get(day).getChildAt(howmuchon_day[day] - 1).getId());
            }
            tv.setHeight(30);
            tv.setLayoutParams(layoutParams);
            tvset.add(tv);
            days.get(day).addView(tv, howmuchon_day[day]);
            howmuchon_day[day] = howmuchon_day[day] + 1;

        }
        //weekview_day = view.findViewById(R.id.calendar_day);
        //weekview_day.setAdapter(new MainActivity_weekly.CustomWeekview_Day());
    }
}