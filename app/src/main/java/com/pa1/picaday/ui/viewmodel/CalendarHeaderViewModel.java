package com.pa1.picaday.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import com.pa1.picaday.data.TSLiveData;

public class CalendarHeaderViewModel extends ViewModel {
    public TSLiveData<Long> mHeaderDate = new TSLiveData<>();

    public void setHeaderDate(long headerDate) {
        this.mHeaderDate.setValue(headerDate);
    }
}
