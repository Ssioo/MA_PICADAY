package com.pa1.picaday.MainActivity_Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pa1.picaday.AddActivity_Fragment.AddActivity_weekly;
import com.pa1.picaday.CustomUI.CustomweeklistAdapter;
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

public class MainActivity_weekly_list extends Fragment {


    private Calendar standardCal_start;
    private Calendar standardCal_end;
    DBManager manager;
    public static final int START_WITH_RESULT = 1000;
    public static final String DATA_CHANGED = "data changed";

    public MainActivity_weekly_list() {
    }

    ArrayList<Dateinfo> thisweek_list = new ArrayList<>();
    long thisweek_left_time;
    long standcal;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    SimpleDateFormat tempSDF = new SimpleDateFormat("d일 H시간 m분", Locale.getDefault());
    SimpleDateFormat tempSDF_d = new SimpleDateFormat("d", Locale.getDefault());
    TextView lefttime;
    private ListView thisweeklist;
    private CustomweeklistAdapter customweeklistAdapter;
    Handler handler;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == EditActivity.RESULT_CODE){
            switch (requestCode){
                case START_WITH_RESULT:
                    thisweek_list = manager.selectAll_thisweek(sdf.format(standardCal_start.getTime()), sdf.format(standardCal_end.getTime()));
                    customweeklistAdapter.addList(thisweek_list);
                    thisweeklist.setAdapter(customweeklistAdapter);
                    break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_weekly_list, container, false);

        standardCal_start = Calendar.getInstance();
        standardCal_end = Calendar.getInstance();
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

        manager = new DBManager(getActivity());
        thisweek_list = manager.selectAll_thisweek(sdf.format(standardCal_start.getTime()), sdf.format(standardCal_end.getTime()));


        /* 오늘 일정 리스트뷰 작성 */
        thisweeklist = view.findViewById(R.id.weeklist);
        customweeklistAdapter = new CustomweeklistAdapter();
        customweeklistAdapter.addList(thisweek_list);
        thisweeklist.setAdapter(customweeklistAdapter);
        thisweeklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > -1 && position < thisweeklist.getCount()) {
                    Dateinfo dateinfo = thisweek_list.get(position);
                    Intent intent = new Intent(getActivity(), EditActivity.class);
                    intent.putExtra(EditActivity.EDIT_INTENT_KEY, dateinfo);
                    startActivityForResult(intent, START_WITH_RESULT);
                }
            }
        });

        /* 삭제 시 listview 갱신 */
        customweeklistAdapter.setOnDataSetChangedListener(new CustomweeklistAdapter.OnDataSetChangedListener() {
            @Override
            public void onDataSetChangedListener(String key) {
                thisweek_list = manager.selectAll_thisweek(sdf.format(standardCal_start.getTime()), sdf.format(standardCal_end.getTime()));
                customweeklistAdapter.addList(thisweek_list);
                thisweeklist.setAdapter(customweeklistAdapter);
            }
        });

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

        /* Text style 세팅 */
        SharedPreferences sdp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        lefttime = view.findViewById(R.id.time_left_thisweek_list);
        if (sdp.getString("timerstyle", "").equals("스타일1")) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.american_captain));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.warm_blue));
        }
        else if (sdp.getString("timerstyle", "").equals("스타일2")) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.baemin_jua));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.coral_red));
        }

        return view;
    }

    private void get_lefttime(long nextweekday) {
        Calendar nowcal = Calendar.getInstance();
        Calendar thismonth = Calendar.getInstance();
        thismonth.set(Calendar.DAY_OF_MONTH, 1);
        thismonth.set(Calendar.HOUR_OF_DAY, 0);
        thismonth.set(Calendar.MINUTE, 0);
        thismonth.set(Calendar.SECOND, 0);
        thisweek_left_time = nextweekday - nowcal.getTime().getTime();
        //Log.e("lefttime", String.valueOf(thisweek_left_time));
        for(int i=0; i<thisweek_list.size(); i++) {
            long nowcallong = nowcal.getTime().getTime();
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
}
