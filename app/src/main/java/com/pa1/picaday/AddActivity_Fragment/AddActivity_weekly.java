package com.pa1.picaday.AddActivity_Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.pa1.picaday.R;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Day;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Deadline;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Time;


public class AddActivity_weekly extends BottomSheetDialogFragment {
    public static AddActivity_weekly getInstance() { return new AddActivity_weekly();    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_weekly, container, false);

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) view.findViewById(R.id.btn_check_week);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = getIntent();

                //startActivity(new Intent(getApplicationContext(), MainActivity_weekly.class));
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
        fragmentTransaction.add(R.id.timeselect_picker_weekly, new Timeselect_Time()).commit();

        /* 시간, 하루종일, 데드라인 형태 변환 체크 */
        RadioGroup chk_group_weekly = (RadioGroup) view.findViewById(R.id.chk_group_weekly);
        chk_group_weekly.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                if (checkedId == R.id.chk_day_weekly) {
                    /* 시간 & 종료 시간 설정 Fragment 호출 */
                    fragmentTransaction.replace(R.id.timeselect_picker_weekly, new Timeselect_Day());
                }
                else if (checkedId == R.id.chk_time_weekly) {
                    /* 시간 & 종료 시간 설정 Fragment 호출 */
                    fragmentTransaction.replace(R.id.timeselect_picker_weekly, new Timeselect_Time());
                }
                else if (checkedId == R.id.chk_deadline_weekly) {
                    /* 시간 & 종료 시간 설정 Fragment 호출 */
                    fragmentTransaction.replace(R.id.timeselect_picker_weekly, new Timeselect_Deadline());
                }
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
