package com.imdvlpr.sobatdompet.helper.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreference {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var context: Context

    fun sharedPreference(context: Context) {
        this.context = context
        sharedPreferences = context.getSharedPreferences(Constants.PREF.PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun saveToPref(strKey: String, value: Any?) {
        when (value) {
            is String   -> editor.putString(strKey, value)
            is Boolean  -> editor.putBoolean(strKey, value)
            is Float    -> editor.putFloat(strKey, value)
            is Int      -> editor.putInt(strKey, value)
            is Long     -> editor.putLong(strKey, value)
        }
        editor.apply()
    }

    fun deleteFromPref(strKey: String) {
        editor.remove(strKey)
        editor.apply()
    }

    fun clearPref() {
        editor.clear()
        editor.apply()
    }

    fun getStringFromPref(strKey: String): String {
        var value = sharedPreferences.getString(strKey, "") ?: ""
        if (value.isBlank()) {
            value = ""
        }
        return value
    }

    fun getBooleanFromPref(strKey: String): Boolean {
        return sharedPreferences.getBoolean(strKey, false)
    }

    fun getIntFromPref(strKey: String): Int {
        return sharedPreferences.getInt(strKey, 0)
    }

    fun getLongFromPref(strKey: String): Long {
        return sharedPreferences.getLong(strKey, 0)
    }
}