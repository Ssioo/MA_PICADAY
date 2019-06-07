package com.pa1.picaday;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;


public class Settingpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) findViewById(R.id.btn_check_setting);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = getIntent();

                //startActivity(new Intent(getApplicationContext(), MainActivity_monthly.class));

                //SharedPreferences style_timer = getSharedPreferences("style_settings", MODE_PRIVATE);

                //final SharedPreferences.Editor editor = style_timer.edit();
                //int style_timer =;
                //final int style_first_show = 1;
                //editor.putInt("style_first_show", style_first_show);
                //editor.putBoolean("style_first_show_day", true);
                //editor.putBoolean("style_first_show_week", false);
                //editor.putBoolean("style_first_show_month", false);
                //editor.commit();

                RadioGroup timeselect = (RadioGroup) findViewById(R.id.timeselectGroup);
                timeselect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.sel_day) {

                        }
                        if (checkedId == R.id.sel_week) {

                        }
                        if (checkedId == R.id.sel_month) {

                        }
                    }
                });
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
