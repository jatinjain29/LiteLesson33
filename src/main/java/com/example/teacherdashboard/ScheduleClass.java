package com.example.teacherdashboard;

import androidx.annotation.NonNull;

class ScheduleClass {
    String day,starttime,endtime,Class;

    public ScheduleClass(String day, String starttime, String endtime, String aClass) {
        this.day = day;
        this.starttime = starttime;
        this.endtime = endtime;
        Class = aClass;
    }

    public String getDay() {
        return day;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }


    public String getCClass() {
        return Class;
    }
}
