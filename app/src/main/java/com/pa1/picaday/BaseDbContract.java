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
        public static final String COLUMN_NAME_START_TIME = "startTime";
        public static final String COLUMN_NAME_END_TIME = "endTime";
        public static final String COLUMN_NAME_CHECKBOX_FIRST = "checkboxFirst";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_WHO = "who";
        public static final String COLUMN_NAME_PRIORITY = "priority";
        public static final String COLUMN_NAME_PARTICIPATION = "participation";
        public static final String COLUMN_NAME_CYCLE = "cycle";
        public static final String COLUMN_NAME_MEMO = "memo";
    }
}
