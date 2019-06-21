package com.pa1.picaday.CustomUI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.pa1.picaday.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DayCirclePin extends View {

    int x, y;
    private static float ANGLE_PER_TIME = (float) 360/86400;
    private final SimpleDateFormat time = new SimpleDateFormat("a h:mm:ss", Locale.getDefault());
    private final Typeface textfont = Typeface.createFromAsset(getContext().getAssets(), "scdream4.otf");

    private Bitmap pin;
    private float rpin;

    public DayCirclePin(Context context, int x, int y) {
        super(context);

        pin = BitmapFactory.decodeResource(context.getResources(), R.drawable.clockpin);
        this.x = x;
        this.y = y;

        mHandler.sendEmptyMessageDelayed(0, 15);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();
            mHandler.sendEmptyMessageDelayed(0, 500);
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Calendar calendar = Calendar.getInstance();
        rpin = ((calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND)) * ANGLE_PER_TIME);


        canvas.rotate(rpin, (float) (x+y)/2, (float) (x+y)/2); // 현재 시간 시계 침 그리기
        canvas.drawBitmap(pin, null, new Rect(521,100,578,580), null);
        canvas.rotate(-rpin, (float) (x+y)/2, (float) (x+y)/2);

        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.warm_grey));
        p.setTypeface(textfont);

        p.setTextSize(60);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(time.format(calendar.getTime()), (x+y) / 2, (x+y) / 2 + 90, p);
    }
}
