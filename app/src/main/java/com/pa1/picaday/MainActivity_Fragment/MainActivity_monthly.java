package com.pa1.picaday.MainActivity_Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.pa1.picaday.R;

import java.util.ArrayList;


public class MainActivity_monthly extends Fragment {

    GridView calendar_day;
    Fragment act = this;

    ArrayList<String> textArr = new ArrayList<>();

    public MainActivity_monthly() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_main_monthly, container, false);


        SharedPreferences sd = getActivity().getSharedPreferences("style_settings", 0);

        TextView lefttime = view.findViewById(R.id.time_left_thismonth);
        if (sd.getBoolean("style_timer1", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.american_captain));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.warm_blue));
        }
        else if (sd.getBoolean("style_timer2", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.baemin_jua));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.coral_red));
        }


        for (int i = 1; i<=31; i++) {
            textArr.add(String.valueOf(i));
        }

        calendar_day = view.findViewById(R.id.calendar_day);
        calendar_day.setAdapter(new CustomCalendar_Day());


        return view;
    }

    public class CustomCalendar_Day extends BaseAdapter {
        LayoutInflater inflater;

        public CustomCalendar_Day() {
            //this.inflater = getLayoutInflater();
            inflater = (LayoutInflater) act.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return textArr.size();
        }

        @Override
        public Object getItem(int position) {
            return textArr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_calendarview_item, parent, false);
            }
            TextView textView = convertView.findViewById(R.id.text_day);
            textView.setText(textArr.get(position));
            return convertView;
        }
    }

}