package com.pa1.picaday.MainActivity_Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.pa1.picaday.CustomUI.CustomdaylistAdapter;
import com.pa1.picaday.CustomUI.Dateinfo;
import com.pa1.picaday.Database.DBManager;
import com.pa1.picaday.Edit_Activity.EditActivity;
import com.pa1.picaday.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity_daily_list extends Fragment {

    public MainActivity_daily_list() {
    }

    ArrayList<Dateinfo> today_list = new ArrayList<>();
    long today_left_time;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    SimpleDateFormat tempSDF = new SimpleDateFormat("H시간 m분 s초", Locale.getDefault());
    TextView lefttime;
    Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_main_daily_list, container, false);

        Calendar standardCal = Calendar.getInstance();
        standardCal.set(standardCal.get(Calendar.YEAR),standardCal.get(Calendar.MONTH),standardCal.get(Calendar.DAY_OF_MONTH), 0,0,0);

        DBManager manager = new DBManager(getActivity());
        today_list = manager.selectAll_today(sdf.format(standardCal.getTime()));


       /* 오늘 일정 리스트뷰 작성 */
        final ListView todaylist = view.findViewById(R.id.daylist);
        CustomdaylistAdapter customdaylistAdapter = new CustomdaylistAdapter();
        customdaylistAdapter.addList(today_list);
        todaylist.setAdapter(customdaylistAdapter);
        final boolean[] check = {false};
        todaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position > -1 && position < todaylist.getCount()){
                    Dateinfo dateinfo = today_list.get(position);
                    Intent intent = new Intent(getActivity(), EditActivity.class);
                    intent.putExtra(EditActivity.EDIT_INTENT_KEY, dateinfo);
                    startActivity(intent);
                    check[0] = true;
                }
            }
        });
        if(check[0] == true){
            Log.e("click", "button");
            customdaylistAdapter.notifyDataSetChanged();
            check[0] = false;
        }

        /* 오늘 남은 시간 계산 */
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                get_lefttime();
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

        lefttime = view.findViewById(R.id.time_left_today_list);
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

    private void get_lefttime() {
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
                if (nowcallong > datacal_ep) {
                    continue; // endtime보다 지났을 때 : 안 빼기
                }
                else if (nowcallong <= datacal_ep && nowcallong > datacal_sp) {
                    today_left_time = today_left_time - (datacal_ep - nowcal.getTime().getTime());
                    // 현재 starttime이랑 endtime 사이에 있을 때
                }
                else {
                    // starttime 전일 때 : 다 빼기
                    today_left_time = today_left_time - (datacal_ep - datacal_sp);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Date today_left = new Date(today_left_time + tommorowcal.getTime().getTime());
        lefttime.setText(tempSDF.format(today_left));
    }
}


