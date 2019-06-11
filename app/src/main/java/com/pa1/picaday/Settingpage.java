package com.pa1.picaday;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;


public class Settingpage extends AppCompatActivity {

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        /* 초기 화면 설정 세팅 */
        final RadioGroup timeselectgroup = findViewById(R.id.timeselectGroup);

        SharedPreferences style_settings = getSharedPreferences("style_settings", MODE_PRIVATE);
        if (style_settings.getBoolean("style_first_show_day", true))
            timeselectgroup.check(R.id.sel_day);
        else if (style_settings.getBoolean("style_first_show_week", false))
            timeselectgroup.check(R.id.sel_week);
        else if (style_settings.getBoolean("style_first_show_month", false))
            timeselectgroup.check(R.id.sel_month);

        /* 주로 몇시에 자고 일어나나요? 세팅 */


        /* 남은 시계 시간 스타일 세팅 */
        final RadioGroup timerstylegroup = findViewById(R.id.timerstyleGroup);

        if (style_settings.getBoolean("style_timer1", true))
            timerstylegroup.check(R.id.timerstyle1);
        else if (style_settings.getBoolean("style_timer2", false))
            timerstylegroup.check(R.id.timerstyle2);


        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) findViewById(R.id.btn_check_setting);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences style_settings = getSharedPreferences("style_settings", MODE_PRIVATE);
                SharedPreferences.Editor editor = style_settings.edit();
                if (timeselectgroup.getCheckedRadioButtonId() == R.id.sel_day) {
                    editor.putBoolean("style_first_show_day", true);
                    editor.putBoolean("style_first_show_week", false);
                    editor.putBoolean("style_first_show_month", false);
                }
                else if (timeselectgroup.getCheckedRadioButtonId() == R.id.sel_week) {
                    editor.putBoolean("style_first_show_day", false);
                    editor.putBoolean("style_first_show_week", true);
                    editor.putBoolean("style_first_show_month", false);
                }
                else if (timeselectgroup.getCheckedRadioButtonId() == R.id.sel_month) {
                    editor.putBoolean("style_first_show_day", false);
                    editor.putBoolean("style_first_show_week", false);
                    editor.putBoolean("style_first_show_month", true);
                }

                if (timerstylegroup.getCheckedRadioButtonId() == R.id.timerstyle1) {
                    editor.putBoolean("style_timer1", true);
                    editor.putBoolean("style_timer2", false);
                }
                else if (timerstylegroup.getCheckedRadioButtonId() == R.id.timerstyle2) {
                    editor.putBoolean("style_timer1", false);
                    editor.putBoolean("style_timer2", true);
                }

                editor.commit();

                finish();
            }
        });

        /* X 버튼 눌렀을 때 */
        ImageButton btn_cancel = (ImageButton) findViewById(R.id.btn_cancel_setting);
        btn_cancel.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
