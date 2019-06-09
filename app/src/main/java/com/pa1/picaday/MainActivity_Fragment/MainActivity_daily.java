package com.pa1.picaday.MainActivity_Fragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pa1.picaday.DayCircleChart;
import com.pa1.picaday.R;
import com.pa1.picaday.WritingVO;

import java.util.ArrayList;


public class MainActivity_daily extends Fragment {

    public MainActivity_daily() {
        // Required empty public constructor
    }

    ArrayList<WritingVO> writing = null;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.activity_main_daily, container, false);


        /* DB에 저장된 오늘 일정들을 표시,
        sp = start_cal의 hour, minute 부분의 long 값
        ep = end-cal의 hour, minute 부분의 long 값 */

        writing = new ArrayList<WritingVO>();
        Canvas canvas = new Canvas();
        writing.add(new WritingVO(0, 144));
        writing.add(new WritingVO(180, 240));

        FrameLayout daychart = view.findViewById(R.id.daychart);
        DayCircleChart dayCircleChart = new DayCircleChart(getActivity(), writing, 200, 900);
        daychart.addView(dayCircleChart);

        return view;
    }
}
