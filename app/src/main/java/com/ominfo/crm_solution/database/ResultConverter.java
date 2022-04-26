package com.ominfo.crm_solution.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ominfo.crm_solution.ui.login.model.LoginResult;

import java.lang.reflect.Type;

public class ResultConverter {
    @TypeConverter
    public static LoginResult fromString(String value) {
        Type listType = new TypeToken<LoginResult>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(LoginResult list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
