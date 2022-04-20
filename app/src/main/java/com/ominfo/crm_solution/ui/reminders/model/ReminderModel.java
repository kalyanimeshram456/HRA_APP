
package com.ominfo.crm_solution.ui.reminders.model;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Entity(tableName = "reminder_data")
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ReminderModel {

    @ColumnInfo(name = "title")
    @Expose
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "date")
    @Expose
    @SerializedName("date")
    private String date;

    @ColumnInfo(name = "status")
    @Expose
    @SerializedName("status")
    private String status;

    @ColumnInfo(name = "time")
    @Expose
    @SerializedName("time")
    private String time;

    @ColumnInfo(name = "value")
    @Expose
    @SerializedName("value")
    private String value;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ID")
    @Expose
    @SerializedName("ID")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ReminderModel{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", time='" + time + '\'' +
                ", value='" + value + '\'' +
                ", id=" + id +
                '}';
    }
}
