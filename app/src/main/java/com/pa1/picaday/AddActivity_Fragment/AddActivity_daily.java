package com.pa1.picaday.AddActivity_Fragment;


import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.pa1.picaday.BaseDbContract;
import com.pa1.picaday.BaseDbHelper;
import com.pa1.picaday.R;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Day;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Deadline;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Time;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class AddActivity_daily extends BottomSheetDialogFragment {

    //private Timeselect_Day timeselect_day = new Timeselect_Day();
    private Timeselect_Time timeselect_time = new Timeselect_Time();
    private Timeselect_Deadline timeselect_deadline = new Timeselect_Deadline();
    private RadioGroup chk_group_daily = null;


    public static AddActivity_daily getInstance() {
        return new AddActivity_daily();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_daily, container, false);

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) view.findViewById(R.id.btn_check_day);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* v 버튼 클릭 시 db 저장 */
                EditText schedule_title = (EditText) view.findViewById(R.id.schedule_title_daily);
                String s_title = schedule_title.getText().toString();
                String s_time = null;
                String e_time = null;
                if(chk_group_daily.getCheckedRadioButtonId() == R.id.chk_time_monthly){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    s_time = simpleDateFormat.format(timeselect_time.start_cal.getTime());
                    e_time = simpleDateFormat.format(timeselect_time.end_cal.getTime());
                } else if(chk_group_daily.getCheckedRadioButtonId() == R.id.chk_deadline_monthly){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    e_time = simpleDateFormat.format(timeselect_deadline.end_cal.getTime());
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

                ContentValues contentValues = new ContentValues();
                contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_TITLE, s_title);
                contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_START_TIME, s_time);
                contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_END_TIME, e_time);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_CHECKBOX_FIRST, checkBox);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_LOCATION, loc);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_WHO, who);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_PRIORITY, prior);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_PARTICIPATION, participate);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_CYCLE, cycle);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_MEMO, mem);

                SQLiteDatabase db = BaseDbHelper.getInstance(getActivity()).getWritableDatabase();
                long newRowId = db.insert(BaseDbContract.baseDbEntry.TABLE_NAME, null, contentValues);

                if (newRowId == -1){
                    Toast.makeText(getActivity(), "저장에 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity(), "일정이 저장되었습니다", Toast.LENGTH_SHORT).show();
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

        /* TImePicker 구동 Fragment*/
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.timeselect_picker_daily, timeselect_time).commit();

        chk_group_daily = (RadioGroup) view.findViewById(R.id.chk_group_daily);
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


        return view;
    }
}
