package com.pa1.picaday.Edit_Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.pa1.picaday.CustomUI.Dateinfo;
import com.pa1.picaday.R;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Day;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Deadline;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Time;

public class EditActivity extends AppCompatActivity {
    private Timeselect_Day timeselect_day = new Timeselect_Day();
    private Timeselect_Time timeselect_time = new Timeselect_Time();
    private Timeselect_Deadline timeselect_deadline = new Timeselect_Deadline();
    private RadioGroup chk_group = null;

    public final static String EDIT_INTENT_KEY = "editKey";

    String title = "";
    int radio = 0;
    private String Start_time = null;
    private String End_time = null;
    private String location = "";
    private String withwhom = "";
    private int priority = 0;
    private int participation = 1;
    private String cycle = "";
    private String memo = "";

    @Override
    protected void onStart() {
        super.onStart();
        /*list fragment에서 intent로 date info 받아오기*/
        Intent intent = getIntent();
        Dateinfo dateinfo = (Dateinfo) intent.getSerializableExtra(EDIT_INTENT_KEY);
        this.title = dateinfo.getTitle();
        this.radio = dateinfo.getType_checked();
        this.Start_time = dateinfo.getStart_time();
        this.End_time = dateinfo.getEnd_time();
        this.location = dateinfo.getLocation();
        this.withwhom = dateinfo.getWithwhom();
        this.priority = dateinfo.getPriority();
        this.participation = dateinfo.getParticipation();
        this.cycle = dateinfo.getCycle();
        this.memo = dateinfo.getMemo();

        EditText setTitle = findViewById(R.id.schedule_title_monthly);
        setTitle.setText(title);
        RadioButton rb1 = findViewById(R.id.chk_time_monthly);
        RadioButton rb2 = findViewById(R.id.chk_day_monthly);
        RadioButton rb3 = findViewById(R.id.chk_deadline_monthly);
        switch (radio){
            case 1:
                rb1.setChecked(true);
                    //시간 설정하기
                break;
            case 2:
                rb2.setChecked(true);
                break;
            case 3:
                rb3.setChecked(true);
                break;
            default:
                Toast.makeText(getApplicationContext(), "시간 형식을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
        EditText setLocation = findViewById(R.id.location);
        setLocation.setText(location);
        EditText setWho = findViewById(R.id.withwhom);
        setWho.setText(withwhom);
        RatingBar setPrior = findViewById(R.id.priority_bar);
        setPrior.setRating(priority);
        CheckBox setParti = findViewById(R.id.participate);
        if(participation == 0){
            setParti.setChecked(false);
        }else {
            setParti.setChecked(true);
        }
//        Spinner setCycle = findViewById(R.id.cycle_monthly)
        EditText setMemo = findViewById(R.id.memo);
        setMemo.setText(memo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_schedule);

        chk_group = (RadioGroup) findViewById(R.id.chk_group_monthly);

        /* TImePicker 구동 Fragment*/
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.timeselect_picker_monthly, timeselect_time).commit();

        chk_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (checkedId == R.id.chk_day_monthly) {
                    /* 시간 & 종료 시간 설정 Fragment 호출 */
                    fragmentTransaction.replace(R.id.timeselect_picker_monthly, timeselect_day);
                }
                else if (checkedId == R.id.chk_time_monthly) {
                    /* 시간 & 종료 시간 설정 Fragment 호출 */
                    fragmentTransaction.replace(R.id.timeselect_picker_monthly, timeselect_time);
                }
                else if (checkedId == R.id.chk_deadline_monthly) {
                    /* 시간 & 종료 시간 설정 Fragment 호출 */
                    fragmentTransaction.replace(R.id.timeselect_picker_monthly, timeselect_deadline);
                }
                fragmentTransaction.commit();
            }
        });

        /* 반복 사이클 결정 */
        Spinner cycle_monthly = (Spinner) findViewById(R.id.cycle_monthly);
        String[] items = new String[]{"없음", "매일", "매주", "격주", "매달"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        cycle_monthly.setAdapter(adapter);
    }
}

