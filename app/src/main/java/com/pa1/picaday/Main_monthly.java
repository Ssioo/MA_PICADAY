package com.pa1.picaday;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.pa1.picaday.databinding.CalendarListBinding;
import com.pa1.picaday.ui.adapter.CalendarAdapter;
import com.pa1.picaday.ui.viewmodel.CalendarListViewModel;

import java.util.ArrayList;

public class Main_monthly extends AppCompatActivity {

    private CalendarListBinding binding;
    private CalendarListViewModel model;

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

        binding = DataBindingUtil.setContentView(this, R.layout.main_monthly);
        model = ViewModelProviders.of(this).get(CalendarListViewModel.class);
        binding.setModel(model);
        binding.setLifecycleOwner(this);

        observe();
        if (model != null) {
            model.initCalendarList();
        }
    }

    private void observe() {
        model.mCalendarList.observe(this, new Observer<ArrayList<Object>>() {
            @Override
            public void onChanged(ArrayList<Object> objects) {
                RecyclerView view = binding.pagerCalendar;
                CalendarAdapter adapter = (CalendarAdapter) view.getAdapter();
                if (adapter != null) {
                    adapter.setCalendarList(objects);
                } else {
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
                    adapter = new CalendarAdapter(objects);
                    view.setLayoutManager(manager);
                    view.setAdapter(adapter);
                    if (model.mCenterPosition >= 0) {
                        view.scrollToPosition(model.mCenterPosition);
                    }
                }
            }
        });
    }
}
