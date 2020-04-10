package com.zhuandian.health.entity;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-10.
 */
public class ScheduleEntity {
    private String title;
    private String content;
    private int type; //1.作息时间提醒， 2。用餐时间禁忌提醒 3。服药时间剂量提醒 4。运动康复提醒
    private String day;
    private int hour;
    private int minute;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
