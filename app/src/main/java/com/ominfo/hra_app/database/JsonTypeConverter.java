package com.ominfo.hra_app.database;

import androidx.room.TypeConverter;

import java.io.UnsupportedEncodingException;

public class JsonTypeConverter {
    @TypeConverter
    public static byte[] encodeJsonResponse(String value) {
        return value.getBytes();
    }

    @TypeConverter
    public static String decodeJsonResponse(byte[] encodedValue) {
        try {
            return new String(encodedValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
