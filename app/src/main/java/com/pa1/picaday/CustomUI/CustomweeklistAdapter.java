package com.pa1.picaday.CustomUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pa1.picaday.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CustomweeklistAdapter extends BaseAdapter {
    private ArrayList<Dateinfo> weekitemlist = new ArrayList<>();
    public CustomweeklistAdapter() {
    }

    @Override
    public int getCount() { return weekitemlist.size(); }

    @Override
    public Object getItem(int position) { return weekitemlist.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_daylistview, parent, false);
        }

        TextView title = convertView.findViewById(R.id.daylist_title);
        TextView description = convertView.findViewById(R.id.daylist_start_end_time);
        title.setText(weekitemlist.get(position).getTitle());

        ArrayList<String> day = getDay(position);

        description.setText(day.get(0) + "요일 "
                + weekitemlist.get(position).getStart_time().substring(10,16) + " ~ "
                + day.get(1) + "요일 "
                + weekitemlist.get(position).getEnd_time().substring(10,16));


        ImageButton btn_daylist_edit = convertView.findViewById(R.id.btn_daylist_edit);
        btn_daylist_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ImageButton btn_daylist_remove = convertView.findViewById(R.id.btn_daylist_remove);
        btn_daylist_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    public void addList(ArrayList<Dateinfo> dinfos) {
        weekitemlist = dinfos;
    }

    public ArrayList<String> getDay(int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        ArrayList<Calendar> day = new ArrayList<>();
        Calendar day_start = Calendar.getInstance();
        Calendar day_end = Calendar.getInstance();
        try {
            day_start.setTime(sdf.parse(weekitemlist.get(position).getStart_time()));
            day_end.setTime(sdf.parse(weekitemlist.get(position).getEnd_time()));
            day.add(day_start);
            day.add(day_end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayList<String> result = new ArrayList<>();
        for (int i =0; i<2 ;i++) {
            switch (day.get(i).get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    result.add("일");
                    break;
                case 2:
                    result.add("월");
                    break;
                case 3:
                    result.add("화");
                    break;
                case 4:
                    result.add("수");
                    break;
                case 5:
                    result.add("목");
                    break;
                case 6:
                    result.add("금");
                    break;
                case 7:
                    result.add("토");
                    break;
            }
        }
        return result;
    }
}
