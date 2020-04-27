package com.offgrid.coupler.data.converter;

import androidx.room.TypeConverter;

import com.offgrid.coupler.data.model.ChatType;


public class ChatTypeConverter {
    @TypeConverter
    public static ChatType stringToType(String value) {
        return value == null ? ChatType.NONE : ChatType.valueOf(value);
    }

    @TypeConverter
    public static String typeToString(ChatType type) {
        return type.toString();
    }

}
