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

import java.text.SimpleDateFormat;
import java.util.Locale;


public class AddActivity_weekly extends BottomSheetDialogFragment {
    private Timeselect_Day timeselect_day = new Timeselect_Day();
    private Timeselect_Time timeselect_time = new Timeselect_Time();
    private Timeselect_Deadline timeselect_deadline = new Timeselect_Deadline();
    RadioGroup chk_group_weekly = null;

    public static AddActivity_weekly getInstance() { return new AddActivity_weekly();    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_weekly, container, false);

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) view.findViewById(R.id.btn_check_week);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* v 버튼 클릭 시 db 저장 */
                EditText schedule_title = (EditText) view.findViewById(R.id.schedule_title_weekly);
                String s_title = schedule_title.getText().toString();
                String s_time = null;
                String e_time = null;
                int typechecked = 2;
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
                //int checkBox
                //EditText location = (EditText) view.findViewById(R.id.location);
                //String loc = location.getText().toString();
                //EditText withWhom = (EditText) view.findViewById(R.id.withwhom);
                //String who = withWhom.getText().toString();
                //int prior
                //int participate
                //int cycle
                //EditText memo = (EditText) view.findViewById(R.id.memo);
                //String mem = memo.getText().toString();


                Dateinfo newdateinfo = new Dateinfo(s_title, s_time, e_time,
                        typechecked, "", "", 2, 0, 0, "");
                DBManager manager = new DBManager(getActivity());
                manager.insertData(newdateinfo);
                Toast.makeText(getActivity(), "일정이 저장되었습니다", Toast.LENGTH_SHORT).show();

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

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.timeselect_picker_weekly, timeselect_time).commit();

        /* 시간, 하루종일, 데드라인 형태 변환 체크 */
        chk_group_weekly = (RadioGroup) view.findViewById(R.id.chk_group_weekly);
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

        return view;
    }
}
