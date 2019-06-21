package com.pa1.picaday.ShowActivity_Fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pa1.picaday.BaseDbContract;
import com.pa1.picaday.BaseDbHelper;
import com.pa1.picaday.R;

//이 페이지를 실행하기 위한 호출이 필요

public class ShowActivity_monthly extends BottomSheetDialogFragment {
    public ShowActivity_monthly(){
    }

    public static ShowActivity_monthly getInstance() {return new ShowActivity_monthly(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.show_monthly, container, false);

        /*List 출력 - 입력 값 필요*/
        ListView listView = view.findViewById(R.id.show_list);

        BaseDbHelper dbHelper = BaseDbHelper.getInstance(getActivity());
//        Cursor cursor = dbHelper.getReadableDatabase()
//                .query(BaseDbContract.baseDbEntry.TABLE_NAME,
//                        null, null, null, null, null, null);


        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT "+BaseDbContract.baseDbEntry.COLUMN_NAME_TITLE
                +" FROM "+BaseDbContract.baseDbEntry.TABLE_NAME
                +" WHERE "+BaseDbContract.baseDbEntry.COLUMN_NAME_START_TIME
                +" BETWEEN \"2019-06-09 00:00:00\"  AND \"2019-06-09 23:59:59\"", null);
        ShowAdapter adapter = new ShowAdapter(getActivity(), cursor);
        listView.setAdapter(adapter);

        /*item click 시 - 추후 구현*/

        return view;
    }

    private static class ShowAdapter extends CursorAdapter{

        public ShowAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView titleText = view.findViewById(android.R.id.text1);
            titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(BaseDbContract.baseDbEntry.COLUMN_NAME_TITLE)));
        }
    }
}
