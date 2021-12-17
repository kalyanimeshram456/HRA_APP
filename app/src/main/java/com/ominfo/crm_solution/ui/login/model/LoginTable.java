
package com.ominfo.crm_solution.ui.login.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "login_data")
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LoginTable {

    @ColumnInfo(name = "base_url")
    @Expose
    @SerializedName("base_url")
    private String mBaseUrl;

    @ColumnInfo(name = "isadmin")
    @Expose
    @SerializedName("isadmin")
    private String mIsadmin;

    @ColumnInfo(name = "name")
    @Expose
    @SerializedName("name")
    private String mName;

    @ColumnInfo(name = "profile_picture")
    @Expose
    @SerializedName("profile_picture")
    private String mProfilePicture;

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

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    public String getIsadmin() {
        return mIsadmin;
    }

    public void setIsadmin(String isadmin) {
        mIsadmin = isadmin;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getProfilePicture() {
        return mProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        mProfilePicture = profilePicture;
    }

}
