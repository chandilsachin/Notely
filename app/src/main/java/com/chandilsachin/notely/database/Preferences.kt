package com.chandilsachin.personal_finance.database

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by sachin on 31/12/17.
 */
class Preferences(context: Context) {

    private val KEY_BUDGET = "budget"
    private val KEY_BUDGET_RECURRENCE = "recurrenceBudget"
    private val KEY_FIRST_APP_START = "firstAppStart"

    private var sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)

    fun setBudget(budget: Float){
        sharedPreference.edit().putFloat(KEY_BUDGET, budget).apply()
    }

    fun getBudget(): Float = sharedPreference.getFloat(KEY_BUDGET, 0f)

    fun setRecurrence(recurrence: Boolean){
        sharedPreference.edit().putBoolean(KEY_BUDGET_RECURRENCE, recurrence).apply()
    }

    fun isRecurrence(): Boolean = sharedPreference.getBoolean(KEY_BUDGET_RECURRENCE, false)

    fun isAppOpeningFirstTime(): Boolean = sharedPreference.getBoolean(KEY_FIRST_APP_START, true)

    fun setOpenedFirstTime(){
        sharedPreference.edit().putBoolean(KEY_FIRST_APP_START, false).apply()
    }


}