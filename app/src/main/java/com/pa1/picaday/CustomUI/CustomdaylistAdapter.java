package com.pa1.picaday.CustomUI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.pa1.picaday.Database.DBManager;
import com.pa1.picaday.MainActivity_Fragment.MainActivity_daily_list;
import com.pa1.picaday.R;

import java.util.ArrayList;

public class CustomdaylistAdapter extends BaseAdapter {

    public interface OnDataSetChangedListener {
        void onDataSetChangedListener(String key);
    }

    private ArrayList<Dateinfo> dayitemlist = new ArrayList<>();
    private boolean delayEnterAnimation = true;
    private boolean animationsLocked = false;
    private OnDataSetChangedListener onDataSetChangedListener;

    public void setOnDataSetChangedListener(OnDataSetChangedListener onDataSetChangedListener){
        this.onDataSetChangedListener = onDataSetChangedListener;
    }

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
        if (!animationsLocked) {
            convertView.setTranslationY(100);
            convertView.setAlpha(0.f);
            convertView.animate().translationY(0).alpha(1.f).setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked =  true;
                        }
                    })
                    .start();
        }

        TextView title = convertView.findViewById(R.id.daylist_title);
        TextView description = convertView.findViewById(R.id.daylist_start_end_time);
        title.setText(dayitemlist.get(pos).getTitle());
        description.setText(dayitemlist.get(pos).getStart_time().substring(11,16) + " ~ " + dayitemlist.get(pos).getEnd_time().substring(11,16));



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
                                Toast.makeText(context, "일정을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                /* 삭제 시 listview 갱신 */
                                if(onDataSetChangedListener != null){
                                    onDataSetChangedListener.onDataSetChangedListener(MainActivity_daily_list.DATA_CHANGED);
                                }
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
