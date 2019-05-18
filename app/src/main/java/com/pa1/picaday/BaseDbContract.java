package com.pa1.picaday;

import android.provider.BaseColumns;

//DB structure definition

public final class BaseDbContract {
    private BaseDbContract(){

    }

    //BaseColumns implementation & definite constant
   public static class baseDbEntry implements BaseColumns {
        //baseDbEntry의 목록은 향후 정하기
        public static final String TABLE_NAME = "baseTable";
        public static final String COLUMN_NAME_TITLE = "name";
        public static final String COLUMN_NAME_DATE_START = "dateStart";
        public static final String COLUMN_NAME_DATE_END = "dateEnd";
        public static final String COLUMN_NAME_TIME_START = "timeStart";
        public static final String COLUMN_NAME_TIME_END = "timeEnd";
        public static final String COLUMN_NAME_ALARM = "alarm";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_MEMO = "memo";

    }
}
