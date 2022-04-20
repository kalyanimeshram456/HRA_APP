package com.ominfo.crm_solution.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ominfo.crm_solution.alarm.alarmslist.OnToggleAlarmListener;
import com.ominfo.crm_solution.ui.lr_number.model.UploadVehicleImage;

import java.lang.reflect.Type;
import java.util.List;

public class ToggleTypeConverter {
    @TypeConverter
    public static OnToggleAlarmListener fromString(String value) {
        Type listType = new TypeToken<OnToggleAlarmListener>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(OnToggleAlarmListener list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
