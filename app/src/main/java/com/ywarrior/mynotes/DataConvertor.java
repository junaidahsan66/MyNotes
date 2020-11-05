package com.ywarrior.mynotes;

import androidx.room.TypeConverter;

import java.util.Date;

public class DataConvertor {
    @TypeConverter
    public static Long toTimeStamp(Date date){
        return date==null?null:date.getTime();
    }

    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp==null?null:new Date(timestamp);
    }

}
