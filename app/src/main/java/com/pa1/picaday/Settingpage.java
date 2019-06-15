package com.pa1.picaday;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Settingpage extends AppCompatActivity {

    private Toolbar toolbar_setting;
    private RadioGroup timeselectgroup;
    private RadioGroup timerstylegroup;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        /* Toolbar 구성 */
        toolbar_setting = findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar_setting);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("설정");

        /* 초기 화면 설정 세팅 */
        timeselectgroup = findViewById(R.id.timeselectGroup);

        SharedPreferences style_settings = getSharedPreferences("style_settings", MODE_PRIVATE);
        if (style_settings.getBoolean("style_first_show_day", true))
            timeselectgroup.check(R.id.sel_day);
        else if (style_settings.getBoolean("style_first_show_week", false))
            timeselectgroup.check(R.id.sel_week);
        else if (style_settings.getBoolean("style_first_show_month", false))
            timeselectgroup.check(R.id.sel_month);

        /* 주로 몇시에 자고 일어나나요? 세팅 */


        /* 남은 시계 시간 스타일 세팅 */
        timerstylegroup = findViewById(R.id.timerstyleGroup);

        if (style_settings.getBoolean("style_timer1", true))
            timerstylegroup.check(R.id.timerstyle1);
        else if (style_settings.getBoolean("style_timer2", false))
            timerstylegroup.check(R.id.timerstyle2);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            case R.id.action_save: {
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
                Toast.makeText(getApplicationContext(), "모든 설정은 앱을 다시 시작해야 적용됩니다", Toast.LENGTH_SHORT).show();

                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
