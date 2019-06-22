package com.pa1.picaday.CustomUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pa1.picaday.AddActivity_Fragment.AddActivity_daily;
import com.pa1.picaday.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CustomdaylistAdapter extends BaseAdapter {

    private ArrayList<Dateinfo> dayitemlist = new ArrayList<>();

    public CustomdaylistAdapter() {
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
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_daylistview, parent, false);
        }

        TextView title = convertView.findViewById(R.id.daylist_title);
        TextView description = convertView.findViewById(R.id.daylist_start_end_time);
        title.setText(dayitemlist.get(position).getTitle());
        description.setText(dayitemlist.get(position).getStart_time().substring(11,16) + " ~ " + dayitemlist.get(position).getEnd_time().substring(11,16));


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

    public void addItem(Dateinfo dinfo) {
        dayitemlist.add(dinfo);
    }

    public void addList(ArrayList<Dateinfo> dinfos) {
        dayitemlist = dinfos;
    }
}
