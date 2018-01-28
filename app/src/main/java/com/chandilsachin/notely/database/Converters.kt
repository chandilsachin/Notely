package com.chandilsachin.personal_finance.database

import android.annotation.SuppressLint
import android.arch.persistence.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date


/**
 * Created by sachin on 24/5/17.
 */
class Converters {


    @SuppressLint("SimpleDateFormat")
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS")

    @TypeConverter
    fun fromTimestamp(value: Date): String {
        val str = simpleDateFormat.format(value)
        return str
    }

    @TypeConverter
    fun dateToTimestamp(date: String): Date {
        return simpleDateFormat.parse(date)
    }

}