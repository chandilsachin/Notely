package com.chandilsachin.personal_finance.database

import android.content.Context
import android.text.format.DateUtils
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * Created by sachin on 28/5/17.
 */
class Date {

    var calendar: Calendar by Delegates.notNull()

    constructor() : this(Calendar.getInstance())

    constructor(calendar: Calendar) {
        this.calendar = calendar
    }

    constructor(millis: Long) {
        this.calendar = Calendar.getInstance()
        this.calendar.timeInMillis = millis
    }

    fun getTime(): Long {
        return calendar.timeInMillis
    }

    fun getPrettyDate(context: Context?): String {
        var prettyDate: String
        prettyDate = if (DateUtils.isToday(calendar.timeInMillis)) "Today, ${DateUtils.formatDateTime(context, calendar.timeInMillis, DateUtils.FORMAT_SHOW_TIME)}"
        else DateUtils.formatDateTime(context, calendar.timeInMillis, DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE or
                DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_ABBREV_ALL)

        return prettyDate
    }

    override fun equals(other: Any?): Boolean {
        if (other is Date)
            return other.calendar.timeInMillis == calendar.timeInMillis
        return false
    }


    override fun hashCode(): Int {
        return Objects.hash(calendar.timeInMillis)
    }

    operator fun minus(value: Date): Int {
        return TimeUnit.DAYS.convert(getTime() - value.getTime(),
                TimeUnit.MILLISECONDS).toInt()
    }

    companion object {
        infix fun past(days: Int): Date {
            return getDateTo(days)
        }

        infix fun ahead(days: Int): Date {
            return getDateTo(days)
        }

        private fun getDateTo(days: Int): Date {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, days)
            return Date(cal)
        }
    }
}