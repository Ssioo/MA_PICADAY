package com.pa1.picaday.AddActivity_Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class AddActivity_monthly extends BottomSheetDialogFragment {
    private Timeselect_Day timeselect_day = new Timeselect_Day();
    private Timeselect_Time timeselect_time = new Timeselect_Time();
    private Timeselect_Deadline timeselect_deadline = new Timeselect_Deadline();
    private RadioGroup chk_group = null;

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
    private EditText schedule_title;
    private EditText location;
    private EditText withWhom;
    private RatingBar priority;
    private CheckBox participate;
    private EditText memo;

    public void setFromSaved(Dateinfo info) {
        this.id = info.getId();
        this.s_title = info.getTitle();
        this.s_time = info.getStart_time();
        this.e_time = info.getEnd_time();
        this.typechecked = info.getType_checked();
        this.loc = info.getLocation();
        this.who = info.getWithwhom();
        this.mem = info.getMemo();
        this.prior_float = (float) info.getPriority();
    }

    public AddActivity_monthly() { }
    public static AddActivity_monthly getInstance() { return new AddActivity_monthly(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_monthly, container, false);

        chk_group = (RadioGroup) view.findViewById(R.id.chk_group_monthly);
        schedule_title = (EditText) view.findViewById(R.id.schedule_title_monthly);
        location = (EditText) view.findViewById(R.id.location);
        withWhom = (EditText) view.findViewById(R.id.withwhom);
        priority = (RatingBar)view.findViewById(R.id.priority_bar);
        participate = (CheckBox)view.findViewById(R.id.participate);
        memo = (EditText) view.findViewById(R.id.memo);

        /* Edit 경우인 것을 판별 */
        if (s_title != "") {
            schedule_title.setText(s_title);

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                if (typechecked == 2) {
                    chk_group.check(R.id.chk_time_monthly);
                    timeselect_time.start_cal.setTime(sdf.parse(s_time));
                    timeselect_time.end_cal.setTime(sdf.parse(e_time));
                }
                else if (typechecked == 3) {
                    chk_group.check(R.id.chk_deadline_monthly);
                    timeselect_deadline.end_cal.setTime(sdf.parse(e_time));
                }
                else if (typechecked == 1) {
                    chk_group.check(R.id.chk_day_monthly);
                    timeselect_day.start_cal.setTime(sdf.parse(s_time));
                    timeselect_day.end_cal.setTime(sdf.parse(e_time));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            location.setText(loc);
            withWhom.setText(who);
            memo.setText(mem);
            priority.setRating(prior_float);
        }

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) view.findViewById(R.id.btn_check_month);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* v 버튼 클릭 시 db 저장 */
                s_title = schedule_title.getText().toString();
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
                loc = location.getText().toString();
                who = withWhom.getText().toString();
                prior_float = priority.getRating();
                prior = Math.round(prior_float);
                Boolean parti_bool = participate.isChecked();
                int parti = (parti_bool)? 1 : 0;
                Spinner cycle_monthly = (Spinner)view.findViewById(R.id.cycle_monthly);
                String cycle = cycle_monthly.getSelectedItem().toString();
                mem = memo.getText().toString();

                Dateinfo newdateinfo = new Dateinfo(s_title, s_time, e_time,
                        typechecked, loc, who, prior, parti, cycle, mem);
                DBManager manager = new DBManager(getActivity());
                manager.insertData(newdateinfo);
                if(id == 0){
                    manager.insertData(newdateinfo);
                    Toast.makeText(getActivity(), "새로운 일정이 저장되었습니다", Toast.LENGTH_SHORT).show();
                }else{
                    manager.updateData(id, newdateinfo);
                    Toast.makeText(getActivity(), "일정이 갱신 되었습니다", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });

        /* X 버튼 눌렀을 때 */
        ImageButton btn_cancel = (ImageButton) view.findViewById(R.id.btn_cancel_month);
        btn_cancel.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.timeselect_picker_monthly, timeselect_time).commit();

        /* 시간, 하루종일, 데드라인 형태 변환 체크 */

        chk_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
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
        Spinner cycle_monthly = (Spinner) view.findViewById(R.id.cycle_monthly);
        String[] items = new String[]{"없음", "매일", "매주", "격주", "매달"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        cycle_monthly.setAdapter(adapter);


        /*select query*/




        return view;
    }
}