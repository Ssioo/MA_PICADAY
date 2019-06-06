package com.pa1.picaday;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


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

                //SharedPreferences.Editor editor = style_timer.edit();
                //int style_timer =;
                //editor.putInt("style_timer", style_timer);
                //editor.commit();
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
