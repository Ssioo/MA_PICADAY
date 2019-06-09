package com.pa1.picaday.AddActivity_Fragment;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.pa1.picaday.BaseDbContract;
import com.pa1.picaday.BaseDbHelper;
import com.pa1.picaday.R;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Day;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Deadline;
import com.pa1.picaday.Timeselect_Fragment.Timeselect_Time;


public class AddActivity_monthly extends BottomSheetDialogFragment {

    public static AddActivity_monthly getInstance() { return new AddActivity_monthly();    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.add_monthly, container, false);

        /* V 버튼 눌렀을 때 */
        ImageButton btn_check = (ImageButton) view.findViewById(R.id.btn_check_month);
        btn_check.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = getIntent();

                /* v 버튼 클릭 시 db 저장 */
                EditText schedule_title = (EditText) view.findViewById(R.id.schedule_title_monthly);
                String s_title = schedule_title.getText().toString();
                //String start_time
                //String end_time
                //int checkBox
                EditText location = (EditText) view.findViewById(R.id.location);
                String loc = location.getText().toString();
                EditText withWhom = (EditText) view.findViewById(R.id.withwhom);
                String who = withWhom.getText().toString();
                //int prior
                //int participate
                //int cycle
                EditText memo = (EditText) view.findViewById(R.id.memo);
                String mem = memo.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_TITLE, s_title);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_START_TIME, start_time);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_END_TIME, end_time);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_CHECKBOX_FIRST, checkBox);
                contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_LOCATION, loc);
                contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_WHO, who);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_PRIORITY, prior);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_PARTICIPATION, participate);
                //contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_CYCLE, cycle);
                contentValues.put(BaseDbContract.baseDbEntry.COLUMN_NAME_MEMO, mem);

                SQLiteDatabase db = BaseDbHelper.getInstance(getActivity()).getWritableDatabase();
                long newRowId = db.insert(BaseDbContract.baseDbEntry.TABLE_NAME, null, contentValues);

                if (newRowId == -1){
                    Toast.makeText(getActivity(), "저장에 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity(), "일정이 저장되었습니다", Toast.LENGTH_SHORT).show();
                }

                //startActivity(new Intent(getApplicationContext(), MainActivity_monthly.class));
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
        fragmentTransaction.add(R.id.timeselect_picker_monthly, new Timeselect_Time()).commit();

        /* 시간, 하루종일, 데드라인 형태 변환 체크 */
        RadioGroup chk_group = (RadioGroup) view.findViewById(R.id.chk_group_monthly);
        chk_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                if (checkedId == R.id.chk_day_monthly) {
                    /* 시간 & 종료 시간 설정 Fragment 호출 */
                    fragmentTransaction.replace(R.id.timeselect_picker_monthly, new Timeselect_Day());
                }
                else if (checkedId == R.id.chk_time_monthly) {
                    /* 시간 & 종료 시간 설정 Fragment 호출 */
                    fragmentTransaction.replace(R.id.timeselect_picker_monthly, new Timeselect_Time());
                }
                else if (checkedId == R.id.chk_deadline_monthly) {
                    /* 시간 & 종료 시간 설정 Fragment 호출 */
                    fragmentTransaction.replace(R.id.timeselect_picker_monthly, new Timeselect_Deadline());
                }
                fragmentTransaction.commit();
            }
        });

        /* 반복 사이클 결정 */
        Spinner cycle_monthly = (Spinner) view.findViewById(R.id.cycle_monthly);
        String[] items = new String[]{"없음", "매일", "매주", "격주", "매달"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        cycle_monthly.setAdapter(adapter);


        return view;
    }

}
