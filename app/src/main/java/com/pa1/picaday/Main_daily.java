package com.pa1.picaday;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main_daily extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_daily);

        /* ADD 버튼 눌렀을 때 */
        ImageButton btn_add = (ImageButton) findViewById(R.id.btn_add_day);
        btn_add.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_daily_add.class);

                startActivity(intent);
            }
        });

        /* Next page 버튼 눌렀을 때 */
        TextView btn_next = (TextView) findViewById(R.id.btn_next_day);
        btn_next.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_monthly.class);

                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                finish();
            }
        });

        /* Prev page 버튼 눌렀을 때 */
        TextView btn_prev = (TextView) findViewById(R.id.btn_prev_day);
        btn_prev.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_weekly.class);

                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                finish();
            }
        });

        /* MYPAGE 버튼 눌렀을 때*/
        ImageButton btn_mypage = (ImageButton) findViewById(R.id.btn_mypage_day);
        btn_mypage.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mypage.class);

                startActivity(intent);
            }
        });

        /* SETTING 버튼 눌렀을 때 */
        ImageButton btn_setting = (ImageButton) findViewById(R.id.btn_setting_day);
        btn_setting.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Settingpage.class);

                startActivity(intent);
            }
        });
    }
}
