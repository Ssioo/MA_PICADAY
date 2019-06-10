package com.pa1.picaday;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

public class DayCircleChart extends View {

    ArrayList<WritingVO> writing = null;
    int[] colorset = getResources().getIntArray(R.array.chart_colorset);

    int x;
    int y;
    private final float START_POINT = -90;

    private final float ANGLE_PER_TIME = (float) 360/288;

    private Bitmap clock_background;
    private Bitmap pin;
    private float rpin;

    public DayCircleChart(Context context, ArrayList<WritingVO> writing, int x, int y) {
        super(context);
        this.writing = writing;
        this.x = x;
        this.y = y;
        clock_background = BitmapFactory.decodeResource(context.getResources(), R.drawable.clockbackground);
        pin = BitmapFactory.decodeResource(context.getResources(), R.drawable.clockpin);

        mHandler.sendEmptyMessageDelayed(0, 15);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();
            mHandler.sendEmptyMessageDelayed(0, 500);
        }
    };

    public void CalcTime() {
        Calendar calendar = Calendar.getInstance();
        rpin = (float) (calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND)) / (float) 86400 * 360;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CalcTime();

        RectF rectF = new RectF(x,x,y,y);
        canvas.drawBitmap(clock_background, null, new Rect(x,x,y,y), null); // 시계 배경 그리기

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(y/24);
        p.setAlpha(0x00);
        p.setColor(getResources().getColor(R.color.more_warm_grey));
        //canvas.drawArc(rectF, 0, 360, false, p); // 배경칠하기


        p.setStrokeCap(Paint.Cap.BUTT);
        for (int i = 0; i < writing.size(); i++) {
            p.setColor(colorset[i % colorset.length]);
            canvas.drawArc(rectF, writing.get(i).getStart_point() * ANGLE_PER_TIME + START_POINT, (writing.get(i).getEnd_point() - writing.get(i).getStart_point()) * ANGLE_PER_TIME, false, p);
        }

        canvas.rotate(rpin, (float) (x+y)/2, (float) (x+y)/2); // 현재 시간 시계 침 그리기
        canvas.drawBitmap(pin, null, new Rect(x,x,y,y), null);
        canvas.rotate(-rpin, (float) (x+y)/2, (float) (x+y)/2);
    }
}
