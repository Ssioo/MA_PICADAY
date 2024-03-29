package com.pa1.picaday.CustomUI;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;

import com.pa1.picaday.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DayCircleChart extends View {

    ArrayList<WritingVO> writing = null;
    int[] colorset = getResources().getIntArray(R.array.chart_colorset);

    int top_left;
    int bottom_right;
    int c_width;
    int c_height;
    private final float START_POINT = -90;

    private float ANGLE_PER_TIME = (float) 360/86400;
    private final SimpleDateFormat time = new SimpleDateFormat("a h:mm:ss", Locale.getDefault());
    private final Typeface textfont = Typeface.createFromAsset(getContext().getAssets(), "scdream4.otf");

    private Bitmap clock_background;


    public DayCircleChart(Context context, ArrayList<WritingVO> writing, int width) {
        super(context);
        this.writing = writing;
        this.top_left = (int) (width * 0.1);
        this.bottom_right = (int) (width * 0.9);
        clock_background = BitmapFactory.decodeResource(context.getResources(), R.drawable.clockbackground);

    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF(top_left, top_left, bottom_right, bottom_right);
        canvas.drawBitmap(clock_background, null, new Rect(top_left, top_left, bottom_right, bottom_right), null); // 시계 배경 그리기

        Paint p = new Paint();

        //p.setColor(getResources().getColor(R.color.more_warm_grey));
        //canvas.drawArc(rectF, 0, 360, false, p); // 배경칠하기



        for (int i = 0; i < writing.size(); i++) {
            p.reset();
            p.setAntiAlias(true);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(bottom_right/24);
            p.setAlpha(0x00);
            p.setStrokeCap(Paint.Cap.BUTT);
            p.setColor(colorset[i % colorset.length]);
            float startAngle = writing.get(i).getStart_point() * ANGLE_PER_TIME + START_POINT;
            float sweepAngle = (writing.get(i).getEnd_point() - writing.get(i).getStart_point()) * ANGLE_PER_TIME;
            canvas.drawArc(rectF, startAngle, sweepAngle, false, p);
            p.reset();
            p.setTextAlign(Paint.Align.CENTER);
            p.setColor(getResources().getColor(R.color.black));
            p.setTextSize(36);
            p.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "scdream4.otf"));
            canvas.rotate(startAngle + 90 + sweepAngle / 2, (top_left + bottom_right) / 2, (top_left + bottom_right)/ 2);
            canvas.drawText(writing.get(i).getTitle(), (top_left + bottom_right) / 2, top_left + 10, p);
            canvas.rotate((startAngle + 90 + sweepAngle / 2) * (-1), (top_left + bottom_right) / 2, (top_left + bottom_right)/ 2);
            // 시간 일정 정보 채우기
        }


    }
}
