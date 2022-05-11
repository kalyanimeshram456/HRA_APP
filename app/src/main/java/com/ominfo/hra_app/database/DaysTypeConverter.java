package com.ominfo.hra_app.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ominfo.hra_app.ui.login.model.LoginDays;

import java.lang.reflect.Type;

public class DaysTypeConverter {
    @TypeConverter
    public static LoginDays fromString(String value) {
        Type listType = new TypeToken<LoginDays>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(LoginDays list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
