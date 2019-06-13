package com.pa1.picaday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pa1.picaday.AddActivity_Fragment.AddActivity_daily;
import com.pa1.picaday.AddActivity_Fragment.AddActivity_monthly;
import com.pa1.picaday.AddActivity_Fragment.AddActivity_weekly;
import com.pa1.picaday.MainActivity_Fragment.MainActivity_daily;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 상단 TAB 레이아웃 구성 */
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic2_week));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic2_day));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic2_month));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        /* 하단 메인 뷰페이저 구성 (DAILY, MONTHLY, WEEKLY 프래그먼트) */
        viewPager = findViewById(R.id.pager);

        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount()); // TabPagerAdapter 호출로 뷰페이저 구성
        viewPager.setAdapter(adapter);

        /* 초기 뷰페이저 SharedPreference정보에 의해 구성 */
        SharedPreferences style_settings = getSharedPreferences("style_settings", MODE_PRIVATE);
        if (style_settings.getBoolean("style_first_show_day", false))
            viewPager.setCurrentItem(1);
        else if (style_settings.getBoolean("style_first_show_week", false))
            viewPager.setCurrentItem(0);
        else if (style_settings.getBoolean("style_first_show_month", false))
            viewPager.setCurrentItem(2);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /* ADD 버튼 눌렀을 때 */
        ImageButton btn_add = (ImageButton) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        AddActivity_weekly addActivity_weekly = AddActivity_weekly.getInstance();
                        addActivity_weekly.show(getSupportFragmentManager(), "add_weekly");
                        break;
                    case 1:
                        AddActivity_daily addActivity_daily = AddActivity_daily.getInstance();
                        addActivity_daily.show(getSupportFragmentManager(),"add_daily");
                        break;
                    case 2:
                        AddActivity_monthly addActivity_monthly = AddActivity_monthly.getInstance();
                        addActivity_monthly.show(getSupportFragmentManager(), "add_monthly");
                        break;
                    default:
                        break;
                }
            }
        });

        /* MYPAGE 버튼 눌렀을 때*/
        ImageButton btn_mypage = (ImageButton) findViewById(R.id.btn_mypage);
        btn_mypage.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignIn_Activity.class);

                startActivity(intent);
            }
        });

        /* SETTING 버튼 눌렀을 때 */
        ImageButton btn_setting = (ImageButton) findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Settingpage.class);

                startActivity(intent);
            }
        });

    }
}
