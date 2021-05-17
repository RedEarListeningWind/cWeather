package com.crtf.weather.data.pojo.colorfulclouds.response;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * 2021-05-06T00:00+08:00
 * @author crtf
 */
public class Datetime {

    private Date date;

    private Time time;

    public Datetime() {
    }

    public Datetime(String datetime) {
        this.setDatetime(datetime);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @JsonGetter
    public String getDatetime() {
        return date + " " + time;
    }

    @JsonSetter
    public void setDatetime(String datetime) {
        this.date = new Date();
        this.time = new Time();
        if (datetime != null && !"".equals(datetime)) {
            String regex = "[T+]";
            String[] split = datetime.split(regex);

            this.date.setDate(split[0]);
            this.time.setTime(split[1]);
        }else {
            throw new NullPointerException("datetime 为: '" + datetime + "'");
        }
    }

    @Override
    public String toString() {
        return date + " " + time;
    }
}
