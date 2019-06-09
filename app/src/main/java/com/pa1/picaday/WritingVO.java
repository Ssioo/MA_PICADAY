package com.pa1.picaday;


public class WritingVO {
    private long start_point = 0;
    private long end_point = 0;

    public WritingVO(long sp, long ep) {
        start_point = sp;
        end_point = ep;
    }

    public long getStart_point() {return start_point;};
    public long getEnd_point() {return end_point;};
}
