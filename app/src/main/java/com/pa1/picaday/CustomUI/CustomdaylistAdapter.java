package com.pa1.picaday.CustomUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pa1.picaday.AddActivity_Fragment.AddActivity_daily;
import com.pa1.picaday.AddActivity_Fragment.AddActivity_monthly;
import com.pa1.picaday.Database.DBManager;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_daylistview, parent, false);
        }

        TextView title = convertView.findViewById(R.id.daylist_title);
        TextView description = convertView.findViewById(R.id.daylist_start_end_time);
        title.setText(dayitemlist.get(pos).getTitle());
        description.setText(dayitemlist.get(pos).getStart_time().substring(11,16) + " ~ " + dayitemlist.get(pos).getEnd_time().substring(11,16));


        ImageButton btn_daylist_edit = convertView.findViewById(R.id.btn_daylist_edit);
        btn_daylist_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Tagged", "Yes");
                AddActivity_daily addActivity_daily = AddActivity_daily.getInstance();
                addActivity_daily.show(((AppCompatActivity) context).getSupportFragmentManager(),"add_daily");
            }
        });
        ImageButton btn_daylist_remove = convertView.findViewById(R.id.btn_daylist_remove);
        btn_daylist_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(dayitemlist.get(pos).getTitle())
                        .setMessage("정말 이 일정을 지우시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBManager dbManager = new DBManager(context.getApplicationContext());
                                dbManager.removeData(dayitemlist.get(pos));
                                Toast.makeText(context, "일정을 삭제했습니다. 새로고침을 해주세요.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();

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
