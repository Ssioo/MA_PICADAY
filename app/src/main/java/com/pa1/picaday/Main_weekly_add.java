package com.pa1.picaday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


public class Main_weekly_add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_weekly_add);

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) findViewById(R.id.btn_check_week);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                //startActivity(new Intent(getApplicationContext(), Main_weekly.class));
                finish();
            }
        });

        /* X 버튼 눌렀을 때 */
        ImageButton btn_cancel = (ImageButton) findViewById(R.id.btn_cancel_week);
        btn_cancel.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
