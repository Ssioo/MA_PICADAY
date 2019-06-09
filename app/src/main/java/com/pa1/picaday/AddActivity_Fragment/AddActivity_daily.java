package com.pa1.picaday.AddActivity_Fragment;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Deadline;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Time;


public class AddActivity_daily extends BottomSheetDialogFragment {
    public static AddActivity_daily getInstance() {
        return new AddActivity_daily();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_daily, container, false);

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) view.findViewById(R.id.btn_check_day);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        /* TImePicker 구동 Fragment*/
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.timeselect_picker_daily, new Timeselect_Time()).commit();

        RadioGroup chk_group_daily = (RadioGroup) view.findViewById(R.id.chk_group_daily);
        chk_group_daily.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                if (checkedId == R.id.chk_deadline_daily) {
                    fragmentTransaction.replace(R.id.timeselect_picker_daily, new Timeselect_Deadline());
                }
                else if (checkedId == R.id.chk_time_daily) {
                    fragmentTransaction.replace(R.id.timeselect_picker_daily, new Timeselect_Time());
                }
                fragmentTransaction.commit();
            }
        });


        return view;
    }
}
