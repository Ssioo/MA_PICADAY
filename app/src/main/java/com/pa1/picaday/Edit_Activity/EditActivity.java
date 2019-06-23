package com.pa1.picaday.Edit_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.pa1.picaday.CustomUI.Dateinfo;
import com.pa1.picaday.Database.DBManager;
import com.pa1.picaday.R;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Day;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Deadline;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Time;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {
    private Timeselect_Day timeselect_day = new Timeselect_Day();
    private Timeselect_Time timeselect_time = new Timeselect_Time();
    private Timeselect_Deadline timeselect_deadline = new Timeselect_Deadline();
    private RadioGroup chk_group = null;
    private EditText setTitle;
    private EditText setLocation;
    private EditText setWho;
    private RatingBar setPrior;
    private CheckBox setParti;
    private EditText setMemo;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;

    private int id = 0;
    private String s_title = "";
    private String s_time;
    private String e_time;
    private String loc;
    private String who;
    private String mem;
    private Float prior_float;
    private int prior;
    private int typechecked;

    public final static String EDIT_INTENT_KEY = "editKey";
    public static final int RESULT_CODE = 9999;

    private String title_input = "";
    private int radio_input = 0;
    private String Start_time_input = null;
    private String End_time_input = null;
    private String location_input = "";
    private String withwhom_input = "";
    private int priority_input = 0;
    private int participation_input = 1;
    private String cycle_input = "";
    private String memo_input = "";

    @Override
    protected void onStart() {
        super.onStart();
        /*list fragment에서 intent로 date info 받아오기*/
        Intent intent = getIntent();
        Dateinfo  dateinfo = (Dateinfo) intent.getSerializableExtra(EDIT_INTENT_KEY);
        this.id = dateinfo.getId();
        this.title_input = dateinfo.getTitle();
        this.Start_time_input = dateinfo.getStart_time();
        this.End_time_input = dateinfo.getEnd_time();
        this.location_input = dateinfo.getLocation();
        this.withwhom_input = dateinfo.getWithwhom();
        this.priority_input = dateinfo.getPriority();
        this.participation_input = dateinfo.getParticipation();
        this.cycle_input = dateinfo.getCycle();
        this.memo_input = dateinfo.getMemo();

        setTitle.setText(title_input);
        setLocation.setText(location_input);
        setWho.setText(withwhom_input);
        setPrior.setRating(priority_input);
        if(participation_input == 0){
            setParti.setChecked(false);
        }else {
            setParti.setChecked(true);
        }
//        Spinner setCycle = findViewById(R.id.cycle_monthly)
        setMemo.setText(memo_input);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_schedule);

        chk_group = (RadioGroup) findViewById(R.id.chk_group_monthly);
        setTitle = (EditText) findViewById(R.id.schedule_title_monthly);
        setLocation = (EditText) findViewById(R.id.location);
        setWho = (EditText) findViewById(R.id.withwhom);
        setPrior = (RatingBar) findViewById(R.id.priority_bar);
        setParti = (CheckBox) findViewById(R.id.participate);
        setMemo = (EditText) findViewById(R.id.memo);

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) findViewById(R.id.btn_check_month);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* v 버튼 클릭 시 db 저장 */
                s_title = setTitle.getText().toString();
                s_time = null;
                e_time = null;
                typechecked = 2;
                if (chk_group.getCheckedRadioButtonId() == R.id.chk_day_monthly) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    s_time = simpleDateFormat.format(timeselect_day.start_cal.getTime());
                    e_time = simpleDateFormat.format(timeselect_day.end_cal.getTime());
                    typechecked = 1;
                } else if(chk_group.getCheckedRadioButtonId() == R.id.chk_time_monthly){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    s_time = simpleDateFormat.format(timeselect_time.start_cal.getTime());
                    e_time = simpleDateFormat.format(timeselect_time.end_cal.getTime());
                    typechecked = 2;
                } else if(chk_group.getCheckedRadioButtonId() == R.id.chk_deadline_monthly){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    e_time = simpleDateFormat.format(timeselect_deadline.end_cal.getTime());
                    typechecked = 3;
                }
                loc = setLocation.getText().toString();
                who = setWho.getText().toString();
                prior_float = setPrior.getRating();
                prior = Math.round(prior_float);
                Boolean parti_bool = setParti.isChecked();
                int parti = (parti_bool)? 1 : 0;
                Spinner cycle_monthly = (Spinner) findViewById(R.id.cycle_monthly);
                String cycle = cycle_monthly.getSelectedItem().toString();
                mem = setMemo.getText().toString();

                Dateinfo newdateinfo = new Dateinfo(s_title, s_time, e_time,
                        typechecked, loc, who, prior, parti, cycle, mem);
                DBManager manager = new DBManager(getApplicationContext());
                manager.updateData(id, newdateinfo);
                Toast.makeText(getApplicationContext(), "일정이 갱신 되었습니다", Toast.LENGTH_SHORT).show();

                setResult(RESULT_CODE);
                finish();
            }
        });

        /* X 버튼 눌렀을 때 */
        ImageButton btn_cancel = findViewById(R.id.btn_cancel_month);
        btn_cancel.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CODE);
                finish();
            }
        });

        /* TImePicker 구동 Fragment*/
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Intent intent = getIntent();
        Dateinfo  dateinfo = (Dateinfo) intent.getSerializableExtra(EDIT_INTENT_KEY);
        this.radio_input = dateinfo.getType_checked();
        rb1 = findViewById(R.id.chk_day_monthly);
        rb2 = findViewById(R.id.chk_time_monthly);
        rb3 = findViewById(R.id.chk_deadline_monthly);
        switch (radio_input){
            case 1:
                rb1.setChecked(true);
                rb2.setChecked(false);
                rb3.setChecked(false);
                fragmentTransaction.add(R.id.timeselect_picker_monthly, timeselect_day).commit();
                //시간 설정하기
                break;
            case 2:
                rb1.setChecked(false);
                rb2.setChecked(true);
                rb3.setChecked(false);
                fragmentTransaction.add(R.id.timeselect_picker_monthly, timeselect_time).commit();
                break;
            case 3:
                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(true);
                fragmentTransaction.add(R.id.timeselect_picker_monthly, timeselect_deadline).commit();
                break;
            default:
                Toast.makeText(getApplicationContext(), "시간 형식을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                break;
        }

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

