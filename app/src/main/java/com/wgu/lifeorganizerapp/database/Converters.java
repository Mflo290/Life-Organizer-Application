package com.wgu.lifeorganizerapp.database;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class Converters {

    @TypeConverter
    public static LocalDate fromString(String value) {
        return value == null ? null : LocalDate.parse(value);
    }

    @TypeConverter
    public static String fromDate(LocalDate date) {
        return date == null ? null : date.toString();
    }

}
