package com.pa1.picaday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pa1.picaday.AddActivity_Fragment.AddActivity_daily;
import com.pa1.picaday.AddActivity_Fragment.AddActivity_monthly;
import com.pa1.picaday.AddActivity_Fragment.AddActivity_weekly;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private DrawerLayout drawerlayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Toolbar 구성 */
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        /* TAB 레이아웃 구성 */
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic2_week));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic2_day));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic2_month));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        /* 뷰페이저 구성 (DAILY, MONTHLY, WEEKLY 프래그먼트) */
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
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });


        /* Navigation Drawer 클릭 이벤트*/
        drawerlayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                drawerlayout = findViewById(R.id.drawerlayout);
                if (!drawerlayout.isDrawerOpen(Gravity.START)) {
                    drawerlayout.openDrawer(Gravity.START);
                }
                return true;
            }

            case R.id.action_add: {
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
                return true;
            }
            case R.id.action_auto: {
                // 일정 자동 세팅
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.e("item", String.valueOf(menuItem.getItemId()));
        switch (menuItem.getItemId()) {
            /* Mypage */
            case R.id.navigation_item_mypage:
                break;
            /* Setting page */
            case R.id.navigation_item_setting:
                Intent intent = new Intent(getApplicationContext(), Settingpage.class);
                Toast.makeText(getApplicationContext(), "SETTING PAGE", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
        }

        //menuItem.setChecked(true);
        drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        drawerlayout = findViewById(R.id.drawerlayout);
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START);
        }
        else {super.onBackPressed();}
    }
}
