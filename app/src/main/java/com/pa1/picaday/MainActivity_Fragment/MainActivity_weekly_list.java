package com.pa1.picaday.MainActivity_Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.pa1.picaday.CustomUI.CustommonthlistAdapter;
import com.pa1.picaday.CustomUI.CustomweeklistAdapter;
import com.pa1.picaday.CustomUI.Dateinfo;
import com.pa1.picaday.Database.DBManager;
import com.pa1.picaday.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity_weekly_list extends Fragment {
    public MainActivity_weekly_list() {
    }

    ArrayList<Dateinfo> thisweek_list = new ArrayList<>();
    long thisweek_left_time = 604800000L;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_weekly_list, container, false);

        Calendar standardCal_start = Calendar.getInstance();
        Calendar standardCal_end = Calendar.getInstance();
        standardCal_start.set(standardCal_start.get(Calendar.YEAR),standardCal_start.get(Calendar.MONTH),standardCal_start.get(Calendar.DAY_OF_MONTH) - standardCal_start.get(Calendar.DAY_OF_WEEK) + 1, 0,0,0);
        standardCal_end.set(standardCal_end.get(Calendar.YEAR),standardCal_end.get(Calendar.MONTH),standardCal_start.get(Calendar.DAY_OF_MONTH) + 7, 0,0,0);
        long standcal = standardCal_start.getTime().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


        DBManager manager = new DBManager(getActivity());
        thisweek_list = manager.selectAll_thisweek(sdf.format(standardCal_start.getTime()), sdf.format(standardCal_end.getTime()));

        for (int i=0; i<thisweek_list.size(); i++) {
            String start = thisweek_list.get(i).getStart_time();
            String end = thisweek_list.get(i).getEnd_time();
            try {
                thisweek_left_time = thisweek_left_time - (sdf_full.parse(end).getTime() - sdf_full.parse(start).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        /* 오늘 일정 리스트뷰 작성 */
        ListView thisweeklist = view.findViewById(R.id.weeklist);
        CustomweeklistAdapter customweeklistAdapter = new CustomweeklistAdapter();
        customweeklistAdapter.addList(thisweek_list);
        thisweeklist.setAdapter(customweeklistAdapter);

        /* Text style 세팅 */
        SharedPreferences sd = getActivity().getSharedPreferences("style_settings", 0);

        TextView lefttime = view.findViewById(R.id.time_left_thisweek_list);
        if (sd.getBoolean("style_timer1", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.american_captain));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.warm_blue));
        }
        else if (sd.getBoolean("style_timer2", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.baemin_jua));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.coral_red));
        }
        Date thisweek_left = new Date(thisweek_left_time);
        SimpleDateFormat tempSDF = new SimpleDateFormat("d:HH:mm", Locale.getDefault());
        lefttime.setText(tempSDF.format(thisweek_left));

        return view;
    }
}
