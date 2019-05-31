package com.pa1.picaday;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.pa1.picaday.AddActivity_Fragment.AddActivity_daily;
import com.pa1.picaday.AddActivity_Fragment.AddActivity_monthly;
import com.pa1.picaday.AddActivity_Fragment.AddActivity_weekly;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("WEEKLY"));
        tabLayout.addTab(tabLayout.newTab().setText("DAILY"));
        tabLayout.addTab(tabLayout.newTab().setText("MONTHLY"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.pager);

        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

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
                Intent intent = new Intent(getApplicationContext(), Mypage.class);

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
