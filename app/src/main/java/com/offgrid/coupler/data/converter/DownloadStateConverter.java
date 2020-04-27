package com.offgrid.coupler.data.converter;

import androidx.room.TypeConverter;

import com.offgrid.coupler.data.model.DownloadState;

public class DownloadStateConverter {
    @TypeConverter
    public static DownloadState stringToType(String value) {
        return DownloadState.valueOf(value);
    }

    @TypeConverter
    public static String typeToString(DownloadState type) {
        return type.toString();
    }

}
