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
        final float START_POINT = -90;

        final float ANGLE_PER_TIME = (float) 360/288;
        float partition = 0;
        float angle = 0;
        RectF rectF = new RectF(x,x,y,y);

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(y/6);
        p.setAlpha(0x00);
        p.setColor(getResources().getColor(R.color.warm_grey));
        canvas.drawArc(rectF, 0, 360, false, p);

        p.setColor(getResources().getColor(R.color.coral_red));
        p.setStrokeCap(Paint.Cap.BUTT);
        for (int i=0; i<writing.size(); i++) {
            canvas.drawArc(rectF, writing.get(i).getStart_point() + START_POINT, (writing.get(i).getEnd_point() - writing.get(i).getStart_point()) * ANGLE_PER_TIME, false, p);
        }


    }
}
