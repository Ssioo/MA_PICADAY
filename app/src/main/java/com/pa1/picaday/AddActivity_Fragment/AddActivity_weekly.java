package com.pa1.picaday.AddActivity_Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
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


public class AddActivity_weekly extends BottomSheetDialogFragment {
    private Timeselect_Day timeselect_day = new Timeselect_Day();
    private Timeselect_Time timeselect_time = new Timeselect_Time();
    private Timeselect_Deadline timeselect_deadline = new Timeselect_Deadline();
    RadioGroup chk_group_weekly = null;

    private int id = 0;
    private String s_title = "";
    private String s_time;
    private String e_time;
    private int typechecked;
    private EditText schedule_title;
    private boolean EDIT_MODE = false;

    public void setFromSaved(Dateinfo info) { // EDIT 상태일 경우
        this.id = info.getId();
        this.s_title = info.getTitle();
        this.s_time = info.getStart_time();
        this.e_time = info.getEnd_time();
        this.typechecked = info.getType_checked();
        this.EDIT_MODE = true;
    }

    public static AddActivity_weekly getInstance() { return new AddActivity_weekly();    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_weekly, container, false);

        chk_group_weekly = (RadioGroup) view.findViewById(R.id.chk_group_weekly);
        schedule_title = (EditText) view.findViewById(R.id.schedule_title_weekly);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        /* Edit 경우인 것을 판별 */
        if (EDIT_MODE) { //TOD : EDIT_MODE로 받기
            schedule_title.setText(s_title);

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                if (typechecked == 2) { // time 형태
                    chk_group_weekly.check(R.id.chk_time_weekly);
                    timeselect_time.start_cal.setTime(sdf.parse(s_time));
                    timeselect_time.end_cal.setTime(sdf.parse(e_time));
                    fragmentTransaction.add(R.id.timeselect_picker_weekly, timeselect_time).commit();
                    timeselect_time.setMODE_EDIT(true);
                }
                else if (typechecked == 3) { // deadline 형태
                    chk_group_weekly.check(R.id.chk_deadline_weekly);
                    timeselect_deadline.end_cal.setTime(sdf.parse(e_time));
                    fragmentTransaction.add(R.id.timeselect_picker_weekly, timeselect_deadline).commit();
                }
                else if (typechecked == 1) { // day 형태
                    chk_group_weekly.check(R.id.chk_day_weekly);
                    fragmentTransaction.add(R.id.timeselect_picker_weekly, timeselect_day).commit();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            fragmentTransaction.add(R.id.timeselect_picker_weekly, timeselect_time).commit(); // 초기 : TOD - typechecked불러오는 형식으로 변경
        }

        /* 시간, 하루종일, 데드라인 형태 변환 체크 */

        chk_group_weekly.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                if (checkedId == R.id.chk_day_weekly) {
                    /* 시간 & 종료 시간 설정 Fragment 호출 */
                    fragmentTransaction.replace(R.id.timeselect_picker_weekly, timeselect_day);
                }
                else if (checkedId == R.id.chk_time_weekly) {
                    /* 시간 & 종료 시간 설정 Fragment 호출 */
                    fragmentTransaction.replace(R.id.timeselect_picker_weekly, timeselect_time);
                }
                else if (checkedId == R.id.chk_deadline_weekly) {
                    /* 시간 & 종료 시간 설정 Fragment 호출 */
                    fragmentTransaction.replace(R.id.timeselect_picker_weekly, timeselect_deadline);
                }
                fragmentTransaction.commit();
            }
        });

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) view.findViewById(R.id.btn_check_week);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* v 버튼 클릭 시 db 저장 */

                s_title = schedule_title.getText().toString();
                s_time = null;
                e_time = null;
                typechecked = 2;
                if (chk_group_weekly.getCheckedRadioButtonId() == R.id.chk_day_weekly) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    s_time = simpleDateFormat.format(timeselect_day.start_cal.getTime());
                    e_time = simpleDateFormat.format(timeselect_day.end_cal.getTime());
                    typechecked = 1;
                } else if(chk_group_weekly.getCheckedRadioButtonId() == R.id.chk_time_weekly){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    s_time = simpleDateFormat.format(timeselect_time.start_cal.getTime());
                    e_time = simpleDateFormat.format(timeselect_time.end_cal.getTime());
                    typechecked = 2;
                } else if(chk_group_weekly.getCheckedRadioButtonId() == R.id.chk_deadline_weekly){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    e_time = simpleDateFormat.format(timeselect_deadline.end_cal.getTime());
                    typechecked = 3;
                }

                Dateinfo newdateinfo = new Dateinfo(s_title, s_time, e_time,
                        typechecked, "", "", 2, 0, "", "");
                DBManager manager = new DBManager(getActivity());
                if (id == 0) {
                    manager.insertData(newdateinfo);
                    Toast.makeText(getActivity(), "일정이 저장되었습니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    manager.updateData(id, newdateinfo);
                    Toast.makeText(getActivity(), "일정이 갱신 되었습니다", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });

        /* X 버튼 눌렀을 때 */
        ImageButton btn_cancel = (ImageButton) view.findViewById(R.id.btn_cancel_week);
        btn_cancel.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });



        return view;
    }
}
