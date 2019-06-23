package com.pa1.picaday.MainActivity_Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pa1.picaday.CustomUI.CustomDialogFragment_Daylist;
import com.pa1.picaday.CustomUI.Dateinfo;
import com.pa1.picaday.Database.DBManager;
import com.pa1.picaday.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity_monthly extends Fragment {

    GridView calendar_day;
    Fragment act = this;

    ArrayList<String> textArr = new ArrayList<>();
    ArrayList<Dateinfo> thismonth_list = new ArrayList<>();
    Calendar calendar;
    TextView calendar_header;
    TextView lefttime;
    Handler handler;
    long thismonth_left_time;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
    SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    SimpleDateFormat tempSDF = new SimpleDateFormat("d일 H시간 m분", Locale.getDefault());
    SimpleDateFormat tempSDF_d = new SimpleDateFormat("d", Locale.getDefault());

    public MainActivity_monthly() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_main_monthly, container, false);

        Calendar standardCal = Calendar.getInstance();

        DBManager manager = new DBManager(getActivity());
        thismonth_list = manager.selectAll_today(sdf.format(standardCal.getTime()));


        SharedPreferences sd = getActivity().getSharedPreferences("style_settings", 0);

        lefttime = view.findViewById(R.id.time_left_thismonth);
        if (sd.getBoolean("style_timer1", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.american_captain));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.warm_blue));
        } else if (sd.getBoolean("style_timer2", false)) {
            lefttime.setTypeface(getActivity().getResources().getFont(R.font.baemin_jua));
            lefttime.setTextColor(getActivity().getResources().getColor(R.color.coral_red));
        }


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
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);

        /* 달력 Header 꾸미기*/
        calendar_header = view.findViewById(R.id.calendar_header);
        calendar_header.setText(String.valueOf(calendar.get(Calendar.YEAR)) + "년 " + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "월");
        ImageButton calendar_prev = view.findViewById(R.id.btn_month_prev);
        calendar_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                calendar.set(Calendar.DATE, 1);
                getCalendar(calendar.getTime(), view);
                }
        });
        ImageButton calendar_next = view.findViewById(R.id.btn_month_next);
        calendar_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DATE, 1);
                getCalendar(calendar.getTime(), view);
                }
        });

        /* 달력 리스트 textArr 채우기 (1~30)*/
        getCalendar(calendar.getTime(), view);

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

    private void getCalendar(Date date, View view) {
        int daynum;
        textArr.clear();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DATE, 1);
        daynum = calendar.get(Calendar.DAY_OF_WEEK);
        Log.e("DAYOFWEEK", String.valueOf(daynum));
        for (int i=1; i< daynum; i++) {
            textArr.add("");
        }
        for (int i=1; i<=calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            textArr.add(String.valueOf(i));
        }
        calendar_header.setText(String.valueOf(calendar.get(Calendar.YEAR)) + "년 " + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "월");

        /* 달력 그리드뷰 아답터 호출 */
        calendar_day = view.findViewById(R.id.calendar_day);
        calendar_day.setAdapter(new CustomCalendar_Day());
    }

    public class CustomCalendar_Day extends BaseAdapter {
        LayoutInflater inflater;

        ArrayList<Dateinfo> smalldayitemlist = new ArrayList<>();
        Calendar tempcal;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        public CustomCalendar_Day() {
            //this.inflater = getLayoutInflater();
            inflater = (LayoutInflater) act.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return textArr.size();
        }

        @Override
        public Object getItem(int position) {
            return textArr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_calendarview_item, parent, false);
            }

            DBManager manager = new DBManager(getActivity());
            if (textArr.get(position) != "") {

                tempcal = Calendar.getInstance();
                tempcal.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(textArr.get(position)));
                smalldayitemlist = manager.selectAll_today(sdf.format(tempcal.getTime()));
            }

            /* 달력 아이템 클릭 이벤트 */
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBManager manager = new DBManager(getActivity());
                    if (textArr.get(position) != "") {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        tempcal = Calendar.getInstance();
                        tempcal.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(textArr.get(position)));
                        smalldayitemlist = manager.selectAll_today(sdf.format(tempcal.getTime()));

                        FragmentManager fm = getChildFragmentManager();
                        CustomDialogFragment_Daylist customDialogFragment_daylist = new CustomDialogFragment_Daylist();
                        customDialogFragment_daylist.setToday_list(smalldayitemlist);
                        customDialogFragment_daylist.setWidth((int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.85));
                        customDialogFragment_daylist.setHeight((int) (getActivity().getResources().getDisplayMetrics().heightPixels * 0.7));
                        Log.e("todaylist_prev", String.valueOf(smalldayitemlist.size()));
                        tempcal = Calendar.getInstance();
                        tempcal.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(textArr.get(position)));
                        customDialogFragment_daylist.setToday(tempcal.getTime());
                        customDialogFragment_daylist.show(fm, "DAYLIST");

                    }
                }
            });

            TextView textView = convertView.findViewById(R.id.text_day);
            textView.setText(textArr.get(position));

            /* smalllist adapter 호출 */
            ListView smalldaylist = convertView.findViewById(R.id.smalldaylist);
            smalldaylist.setAdapter(new SmallDaylist(smalldayitemlist));

            return convertView;
        }

        public class SmallDaylist extends BaseAdapter {

            ArrayList<Dateinfo> dayitemlist = new ArrayList<>();
            int[] colorset = getResources().getIntArray(R.array.chart_colorset);

            public SmallDaylist(ArrayList<Dateinfo> dayitemlist) {
                this.dayitemlist = dayitemlist;
            }

            @Override
            public int getCount() {
                return dayitemlist.size();
            }

            @Override
            public Object getItem(int position) {
                return dayitemlist.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final Context context = parent.getContext();
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.custom_smalldaylist, parent, false);
                }

                TextView smalldaylisttitle = convertView.findViewById(R.id.smalldaylist_title);
                smalldaylisttitle.setText(dayitemlist.get(position).getTitle());
                LinearLayout smalldaylistitem = convertView.findViewById(R.id.smalldaylist_item);
                smalldaylistitem.setBackgroundColor(colorset[position % colorset.length]);

                return convertView;
            }

            public void addList(ArrayList<Dateinfo> dinfos) {
                dayitemlist = dinfos;
            }
        }
    }

}