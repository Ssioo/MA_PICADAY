package com.pa1.picaday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Main_monthly extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_monthly);

        /* ADD 버튼 눌렀을 때 */
        ImageButton btn_add = (ImageButton) findViewById(R.id.btn_add_month);
        btn_add.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_monthly_add.class);

                startActivity(intent);
            }
        });

        /* Next page 버튼 눌렀을 때 */
        TextView btn_next = (TextView) findViewById(R.id.btn_next_month);
        btn_next.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_weekly.class);

                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                finish();
            }
        });

        /* Prev page 버튼 눌렀을 때 */
        TextView btn_prev = (TextView) findViewById(R.id.btn_prev_month);
        btn_prev.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_daily.class);

                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                finish();
            }
        });
    }
}
