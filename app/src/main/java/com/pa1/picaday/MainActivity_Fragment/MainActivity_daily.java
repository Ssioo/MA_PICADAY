package com.pa1.picaday.MainActivity_Fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pa1.picaday.DayCircleChart;
import com.pa1.picaday.R;
import com.pa1.picaday.WritingVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity_daily extends Fragment {

    public MainActivity_daily() {
        // Required empty public constructor
    }

    ArrayList<WritingVO> writing = null;
    long today_left_time = 86400000;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.activity_main_daily, container, false);


        /* DB에 저장된 오늘 일정들을 표시,
        sp = start_cal의 hour, minute 부분의 long 값 (0~86400)
        ep = end-cal의 hour, minute 부분의 long 값 (0~86400)*/

        writing = new ArrayList<WritingVO>();
        Canvas canvas = new Canvas();

        ArrayList<Pair<Date, Date>> datacal = new ArrayList<>();
        Calendar standardCal = Calendar.getInstance();
        standardCal.set(standardCal.get(Calendar.YEAR),standardCal.get(Calendar.MONTH),standardCal.get(Calendar.DAY_OF_MONTH), 0,0,0);
        long standcal = standardCal.getTime().getTime();
        Calendar newStartCal = Calendar.getInstance();
        Calendar newEndCal = Calendar.getInstance();

        /* Test Case */
        newStartCal.set(2019, 6, 11, 0, 0, 0);
        newEndCal.set(2019, 6, 11, 8, 0, 0);
        datacal.add(new Pair(newStartCal.getTime(), newEndCal.getTime()));
        newStartCal.set(2019, 6, 11, 9, 0, 0);
        newEndCal.set(2019, 6, 11, 11, 45, 0);
        datacal.add(new Pair(newStartCal.getTime(), newEndCal.getTime()));
        newStartCal.set(2019, 6, 11, 13, 30, 0);
        newEndCal.set(2019, 6, 11, 14, 45, 0);
        datacal.add(new Pair(newStartCal.getTime(), newEndCal.getTime()));
        newStartCal.set(2019, 6, 11, 16, 30, 0);
        newEndCal.set(2019, 6, 11, 17, 45, 0);
        datacal.add(new Pair(newStartCal.getTime(), newEndCal.getTime()));
        newStartCal.set(2019, 6, 11, 18, 0, 0);
        newEndCal.set(2019, 6, 11, 21, 50, 0);
        datacal.add(new Pair(newStartCal.getTime(), newEndCal.getTime()));
        /* Test Case */


        for(int i=0; i<datacal.size(); i++) {
            writing.add(new WritingVO((datacal.get(i).first.getTime() - standcal)/1000, (datacal.get(i).second.getTime() - standcal)/1000));
            today_left_time = today_left_time - (datacal.get(i).second.getTime() - datacal.get(i).first.getTime());
        }

        FrameLayout daychart = view.findViewById(R.id.daychart);
        DayCircleChart dayCircleChart = new DayCircleChart(getActivity(), writing, 100, 1000);
        daychart.addView(dayCircleChart);

        TextView lefttime = view.findViewById(R.id.time_left_today);
        Date today_left = new Date(today_left_time + standcal);
        SimpleDateFormat tempSDF = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        lefttime.setText(tempSDF.format(today_left));

        return view;
    }

}
