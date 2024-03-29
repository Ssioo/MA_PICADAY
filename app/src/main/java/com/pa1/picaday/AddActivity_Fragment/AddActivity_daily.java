package com.pa1.picaday.AddActivity_Fragment;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
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
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Deadline;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class AddActivity_daily extends BottomSheetDialogFragment {

    //private Timeselect_Day timeselect_day = new Timeselect_Day();
    private Timeselect_Time timeselect_time = new Timeselect_Time();
    private Timeselect_Deadline timeselect_deadline = new Timeselect_Deadline();
    private RadioGroup chk_group_daily = null;

    private int id = 0;
    private String s_title = "";
    private String s_time;
    private String e_time;
    private int typechecked;
    private EditText schedule_title;
    private boolean EDIT_MODE = false;

    public void setEDIT_MODE(boolean EDIT_MODE) {
        this.EDIT_MODE = EDIT_MODE;
    }

    public void setFromSaved(Dateinfo info) {
        this.id = info.getId();
        this.s_title = info.getTitle();
        this.s_time = info.getStart_time();
        this.e_time = info.getEnd_time();
        this.typechecked = info.getType_checked();
        EDIT_MODE = true;
    }


    public static AddActivity_daily getInstance() {
        return new AddActivity_daily();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_daily, container, false);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        chk_group_daily = (RadioGroup) view.findViewById(R.id.chk_group_daily);
        schedule_title = view.findViewById(R.id.schedule_title_daily);

        /* Edit 경우인 것을 판별 */
        if (EDIT_MODE) {
            schedule_title.setText(s_title);

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                if (typechecked == 2) {
                    chk_group_daily.check(R.id.chk_time_daily);
                    timeselect_time.start_cal.setTime(sdf.parse(s_time));
                    timeselect_time.end_cal.setTime(sdf.parse(e_time));
                    timeselect_time.setMODE_EDIT(true);
                    fragmentTransaction.add(R.id.timeselect_picker_daily, timeselect_time).commit();
                }
                else if (typechecked == 3) {
                    chk_group_daily.check(R.id.chk_deadline_daily);
                    timeselect_deadline.end_cal.setTime(sdf.parse(e_time));
                    timeselect_deadline.setMODE_EDIT(true);
                    fragmentTransaction.add(R.id.timeselect_picker_daily, timeselect_deadline).commit();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            fragmentTransaction.add(R.id.timeselect_picker_daily, timeselect_time).commit();
        }

        /* TImePicker 구동 Fragment*/
        chk_group_daily.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                if (checkedId == R.id.chk_deadline_daily) {
                    fragmentTransaction.replace(R.id.timeselect_picker_daily, timeselect_deadline);
                }
                else if (checkedId == R.id.chk_time_daily) {
                    fragmentTransaction.replace(R.id.timeselect_picker_daily, timeselect_time);
                }
                fragmentTransaction.commit();
            }
        });

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) view.findViewById(R.id.btn_check_day);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* v 버튼 클릭 시 db 저장 */
                s_title = schedule_title.getText().toString();
                s_time = null;
                e_time = null;
                typechecked = 2;
                if(chk_group_daily.getCheckedRadioButtonId() == R.id.chk_time_daily){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    s_time = simpleDateFormat.format(timeselect_time.start_cal.getTime());
                    e_time = simpleDateFormat.format(timeselect_time.end_cal.getTime());
                    typechecked = 2;
                } else if(chk_group_daily.getCheckedRadioButtonId() == R.id.chk_deadline_daily){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    e_time = simpleDateFormat.format(timeselect_deadline.end_cal.getTime());
                    typechecked = 3;
                }

                Dateinfo newdateinfo = new Dateinfo(s_title, s_time, e_time,
                        typechecked, "", "", 2, 0, "", "");
                DBManager manager = new DBManager(getActivity());
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
        ImageButton btn_cancel = (ImageButton) view.findViewById(R.id.btn_cancel_day);
        btn_cancel.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        return view;
    }
}
