
package com.ominfo.app.ui.login.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@Entity(tableName = "login")
@SuppressWarnings("unused")
public class CommonLoginResponse {

    @ColumnInfo(name = "response")
    @Expose
    @SerializedName("response")
    private String mResponse;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Expose
    @SerializedName("id")
    private int id;

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        mResponse = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
