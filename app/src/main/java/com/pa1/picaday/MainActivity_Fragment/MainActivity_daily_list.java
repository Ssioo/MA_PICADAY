package com.pa1.picaday.MainActivity_Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pa1.picaday.AddActivity_Fragment.AddActivity_daily;
import com.pa1.picaday.CustomUI.CustomdaylistAdapter;
import com.pa1.picaday.CustomUI.Dateinfo;
import com.pa1.picaday.Database.DBManager;
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
    long today_left_time = 86400000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_main_daily_list, container, false);

        Calendar standardCal = Calendar.getInstance();
        standardCal.set(standardCal.get(Calendar.YEAR),standardCal.get(Calendar.MONTH),standardCal.get(Calendar.DAY_OF_MONTH), 0,0,0);
        long standcal = standardCal.getTime().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


        DBManager manager = new DBManager(getActivity());
        today_list = manager.selectAll_today(sdf.format(standardCal.getTime()));

        /* 오늘 남은 시간 계산 */
        for (int i=0; i<today_list.size(); i++) {
            String start = today_list.get(i).getStart_time();
            String end = today_list.get(i).getEnd_time();
            try {
                today_left_time = today_left_time - (sdf_full.parse(end).getTime() - sdf_full.parse(start).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


       /* 오늘 일정 리스트뷰 작성 */
        final ListView todaylist = view.findViewById(R.id.daylist);
        CustomdaylistAdapter customdaylistAdapter = new CustomdaylistAdapter();
        customdaylistAdapter.addList(today_list);
        todaylist.setAdapter(customdaylistAdapter);
        final boolean[] check = {false};
        todaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("click", "button");
                if(position > -1 && position < todaylist.getCount()){
                    Dateinfo dateinfo = today_list.get(position);
                    AddActivity_daily addActivity_daily = AddActivity_daily.getInstance();
                    addActivity_daily.setFromSaved(dateinfo);
                    addActivity_daily.show(getActivity().getSupportFragmentManager(),"add_daily");
                    check[0] = true;
                }
            }
        });
        if(check[0] == true){
            customdaylistAdapter.notifyDataSetChanged();
            check[0] = false;
        }


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
        Date today_left = new Date(today_left_time + standcal);
        SimpleDateFormat tempSDF = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        lefttime.setText(tempSDF.format(today_left));

        return view;
    }
}
