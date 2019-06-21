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

import com.pa1.picaday.CustomUI.CustomdaylistAdapter;
import com.pa1.picaday.CustomUI.CustommonthlistAdapter;
import com.pa1.picaday.CustomUI.Dateinfo;
import com.pa1.picaday.Database.DBManager;
import com.pa1.picaday.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity_monthly_list extends Fragment {
    public MainActivity_monthly_list() {
    }

    ArrayList<Dateinfo> thismonth_list = new ArrayList<>();
    long thismonth_left_time = 2592000000L;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_monthly_list, container, false);

        Calendar standardCal = Calendar.getInstance();
        standardCal.set(standardCal.get(Calendar.YEAR),standardCal.get(Calendar.MONTH),standardCal.get(Calendar.DAY_OF_MONTH), 0,0,0);
        long standcal = standardCal.getTime().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


        DBManager manager = new DBManager(getActivity());
        thismonth_list = manager.selectAll_today(sdf.format(standardCal.getTime()));

        for (int i=0; i<thismonth_list.size(); i++) {
            String start = thismonth_list.get(i).getStart_time();
            String end = thismonth_list.get(i).getEnd_time();
            try {
                thismonth_left_time = thismonth_left_time - (sdf_full.parse(end).getTime() - sdf_full.parse(start).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        /* 오늘 일정 리스트뷰 작성 */
        ListView thismonthlist = view.findViewById(R.id.monthlist);
        CustommonthlistAdapter custommonthlistAdapter = new CustommonthlistAdapter();
        custommonthlistAdapter.addList(thismonth_list);
        thismonthlist.setAdapter(custommonthlistAdapter);

        /* Text style 세팅 */
        SharedPreferences sd = getActivity().getSharedPreferences("style_settings", 0);

        TextView lefttime = view.findViewById(R.id.time_left_thismonth_list);
        if (sd.getBoolean("style_timer1", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.american_captain));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.warm_blue));
        }
        else if (sd.getBoolean("style_timer2", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.baemin_jua));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.coral_red));
        }
        Date thismonth_left = new Date(thismonth_left_time + standcal);
        SimpleDateFormat tempSDF = new SimpleDateFormat("d:HH:mm", Locale.getDefault());
        lefttime.setText(tempSDF.format(thismonth_left));

        return view;
    }
}
