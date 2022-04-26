package com.ominfo.crm_solution.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ominfo.crm_solution.ui.login.model.LoginTable;

import java.lang.reflect.Type;

public class DetailsConverter {
    @TypeConverter
    public static LoginTable fromString(String value) {
        Type listType = new TypeToken<LoginTable>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(LoginTable list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
