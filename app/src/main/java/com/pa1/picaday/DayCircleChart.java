package com.pa1.picaday;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import java.util.ArrayList;

public class DayCircleChart extends View {

    ArrayList<WritingVO> writing = null;
    int x;
    int y;

    public DayCircleChart(Context context, ArrayList<WritingVO> writing, int x, int y) {
        super(context);
        this.writing = writing;
        this.x = x;
        this.y = y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final float START_POINT = -90f;

        final float ANGLE_PER_TIME = (float) 360/288;
        float successPoint = (float)writing.get(0).getTotal_success()/(float)writing.get(0).getTot_stamp_cnt()*100;
        successPoint = Math.round(successPoint*10);

        successPoint = successPoint/(float)10.0;


        float angle = successPoint*ANGLE_PER_TIME;


        RectF rectF = new RectF(x,x,y,y);

        Paint p = new Paint();

        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(y/6);
        p.setAlpha(0x00);
        p.setColor(Color.GRAY);

        canvas.drawArc(rectF, START_POINT, -360 + angle, false, p);

        p.setColor(Color.RED);
        p.setStrokeCap(Paint.Cap.BUTT);

        canvas.drawArc(rectF, START_POINT, angle, false, p);

        p.reset();
        p.setColor(Color.BLACK);


    }
}
