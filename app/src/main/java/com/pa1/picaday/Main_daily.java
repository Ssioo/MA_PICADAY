package com.pa1.picaday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

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
                finish();
            }
        });

        /* Next page 버튼 눌렀을 때 */
        TextView btn_next = (TextView) findViewById(R.id.btn_next_day);
        btn_next.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_monthly.class);

                startActivity(intent);
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
                finish();
            }
        });
    }
}
