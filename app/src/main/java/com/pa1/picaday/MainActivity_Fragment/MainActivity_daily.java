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

        writing = new ArrayList<WritingVO>();
        Canvas canvas = new Canvas();
        WritingVO writingVO = new WritingVO((float) 50,(float) 100);
        //WritingVO writingVO1 = new WritingVO((float) 1, (float) 10);
        writing.add(writingVO);
        //writing.add(writingVO1);

        FrameLayout daychart = view.findViewById(R.id.daychart);
        DayCircleChart dayCircleChart = new DayCircleChart(getActivity(), writing, 100, 800);
        daychart.addView(dayCircleChart);

        return view;
    }
}
