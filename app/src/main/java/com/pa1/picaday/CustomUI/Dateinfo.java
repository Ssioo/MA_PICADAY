package com.pa1.picaday.CustomUI;


public class Dateinfo {
    //private int id = 0;
    private String title = "";
    private String Start_time = null;
    private String End_time = null;
    private int start_day;
    private int end_day;
    private int type_checked = 0;
    private String location = "";
    private String withwhom = "";
    private int priority = 0;
    private int participation = 1;
    private String cycle = "";
    private String memo = "";

    public Dateinfo(String title,
                    String start_time,
                    String end_time,
                    int type_checked,
                    String location,
                    String withwhom,
                    int priority,
                    int participation,
                    String cycle,
                    String memo) {

        this.title = title;
        Start_time = start_time;
        End_time = end_time;
        this.type_checked = type_checked;
        this.location = location;
        this.withwhom = withwhom;
        this.priority = priority;
        this.participation = participation;
        this.cycle = cycle;
        this.memo = memo;
    }

    //public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public String getStart_time() { return Start_time; }
    public String getEnd_time() { return End_time; }
    public int getPriority() { return priority; }
    public int getType_checked() { return type_checked; }
    public String getLocation() { return location; }
    public String getWithwhom() { return withwhom; }
    public int getParticipation() { return participation; }
    public String getCycle() {return cycle;}
    public String getMemo() { return memo; }
}
