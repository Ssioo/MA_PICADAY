package com.pa1.picaday.MainActivity_Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pa1.picaday.AddActivity_Fragment.AddActivity_monthly;
import com.pa1.picaday.CustomUI.CustomdaylistAdapter;
import com.pa1.picaday.CustomUI.CustommonthlistAdapter;
import com.pa1.picaday.CustomUI.Dateinfo;
import com.pa1.picaday.Database.DBManager;
import com.pa1.picaday.Edit_Activity.EditActivity;
import com.pa1.picaday.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity_monthly_list extends Fragment {

    private ListView thismonthlist;
    private CustommonthlistAdapter custommonthlistAdapter;
    private DBManager manager;
    private Calendar standardCal;
    public static final int START_WITH_RESULT = 1000;
    public static final String DATA_CHANGED = "data changed";


    public MainActivity_monthly_list() {
    }

    ArrayList<Dateinfo> thismonth_list = new ArrayList<>();
    long thismonth_left_time;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
    SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    SimpleDateFormat tempSDF = new SimpleDateFormat("d일 H시간 m분", Locale.getDefault());
    SimpleDateFormat tempSDF_d = new SimpleDateFormat("d", Locale.getDefault());
    TextView lefttime;
    Handler handler;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == EditActivity.RESULT_CODE){
            switch (requestCode){
                case START_WITH_RESULT:
                    thismonth_list = manager.selectAll_today(sdf.format(standardCal.getTime()));
                    custommonthlistAdapter.addList(thismonth_list);
                    thismonthlist.setAdapter(custommonthlistAdapter);
                    break;
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_monthly_list, container, false);

        standardCal = Calendar.getInstance();

        manager = new DBManager(getActivity());
        thismonth_list = manager.selectAll_today(sdf.format(standardCal.getTime()));

        /* 이번 달 남은 일자 계산 */
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                get_lefttime();
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        /* 오늘 일정 리스트뷰 작성 */
        thismonthlist = view.findViewById(R.id.monthlist);
        custommonthlistAdapter = new CustommonthlistAdapter();
        custommonthlistAdapter.addList(thismonth_list);
        thismonthlist.setAdapter(custommonthlistAdapter);
        final boolean[] check = {false};
        thismonthlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > -1 && position < thismonthlist.getCount()) {
                    Dateinfo dateinfo = thismonth_list.get(position);
                    Intent intent = new Intent(getActivity(), EditActivity.class);
                    intent.putExtra(EditActivity.EDIT_INTENT_KEY, dateinfo);
                    startActivityForResult(intent, START_WITH_RESULT);
                }
            }
        });

        /* 삭제 시 listview 갱신 */
        custommonthlistAdapter.setOnDataSetChangedListener(new CustommonthlistAdapter.OnDataSetChangedListener() {
            @Override
            public void onDataSetChangedListener(String key) {
                thismonth_list = manager.selectAll_today(sdf.format(standardCal.getTime()));
                custommonthlistAdapter.addList(thismonth_list);
                thismonthlist.setAdapter(custommonthlistAdapter);
            }
        });


        /* Text style 세팅 */
        SharedPreferences sdp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        lefttime = view.findViewById(R.id.time_left_thismonth_list);
        if (sdp.getString("timerstyle", "").equals("스타일1")) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.american_captain));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.warm_blue));
        }
        else if (sdp.getString("timerstyle", "").equals("스타일2")) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.baemin_jua));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.coral_red));
        }

        return view;
    }

    private void get_lefttime() {
        Calendar nowcal = Calendar.getInstance();
        long nowcallong = nowcal.getTime().getTime();
        Calendar nextmonthcal = Calendar.getInstance();
        nextmonthcal.add(Calendar.MONTH, 1);
        nextmonthcal.set(Calendar.DAY_OF_MONTH, 1);
        nextmonthcal.set(Calendar.HOUR_OF_DAY, 0);
        nextmonthcal.set(Calendar.MINUTE, 0);
        nextmonthcal.set(Calendar.SECOND, 0);
        thismonth_left_time = nextmonthcal.getTime().getTime() - nowcallong;
        for(int i=0; i<thismonth_list.size(); i++) {
            long datacal_sp = 0;
            long datacal_ep = 0;
            try {
                datacal_sp = sdf_full.parse(thismonth_list.get(i).getStart_time()).getTime();
                datacal_ep = sdf_full.parse(thismonth_list.get(i).getEnd_time()).getTime();
                if (nowcallong > datacal_ep) {
                    continue; // endtime보다 지났을 때 : 안 빼기
                }
                else if (nowcallong > datacal_sp) {
                    thismonth_left_time = thismonth_left_time - (datacal_ep - nowcal.getTime().getTime());
                    // 현재 starttime이랑 endtime 사이에 있을 때
                }
                else {
                    // starttime 전일 때 : 다 빼기
                    thismonth_left_time = thismonth_left_time - (datacal_ep - datacal_sp);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Date thismonth_left = new Date(thismonth_left_time + nextmonthcal.getTime().getTime());
        String tempstr = String.valueOf(Integer.parseInt(tempSDF_d.format(thismonth_left)) - 1) + tempSDF.format(thismonth_left).substring(tempSDF.format(thismonth_left).indexOf("일"));
        lefttime.setText(tempstr);
    }
}
