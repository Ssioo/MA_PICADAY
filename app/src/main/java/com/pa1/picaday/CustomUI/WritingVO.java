package com.pa1.picaday.CustomUI;


public class WritingVO {
    private long start_point = 0;
    private long end_point = 0;
    private String title = "";

    public WritingVO(long sp, long ep, String title) {
        start_point = sp;
        end_point = ep;
        this.title = title;
    }

    public long getStart_point() {return start_point;};
    public long getEnd_point() {return end_point;};
    public String getTitle() { return title; }
}
