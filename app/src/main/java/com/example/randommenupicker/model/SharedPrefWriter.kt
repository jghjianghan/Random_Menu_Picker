package com.example.randommenupicker.model

import android.content.Context
import android.content.SharedPreferences

class SharedPrefWriter(context: Context) {
    var sharedPref: SharedPreferences
    companion object {
        const val NAMA_SHARED_PREF = "random_menu_pref"
        const val KEY_RANDOM_LIMIT = "RANDOM_LIMIT"
        const val KEY_SEARCH_HISTORY_STATUS = "SEARCH_HISTORY_STATUS"
        const val KEY_SEARCH_QUERY = "SEARCH_QUERY"
        const val KEY_NAMA_MENU = "NAMA_MENU"
        const val KEY_DESKRIPSI = "DESKRIPSI"
        const val KEY_BAHAN = "BAHAN"
        const val KEY_LANGKAH = "LANGKAH"
        const val KEY_TAG = "TAG"
        const val KEY_RESTO = "RESTO"
    }

    init {
        this.sharedPref = context.getSharedPreferences(NAMA_SHARED_PREF, 0)
    }

    fun saveRandomLimit(limit:Int){
        val editor = sharedPref.edit()
        editor.putInt(KEY_RANDOM_LIMIT, limit)
        editor.commit()
    }
    fun getRandomLimit(): Int{
        return sharedPref.getInt(KEY_RANDOM_LIMIT, 0)
    }

    fun saveSearchHistoryStatus(status: Boolean){
        val editor = sharedPref.edit()
        editor.putBoolean(KEY_SEARCH_HISTORY_STATUS, status)
        editor.commit()
    }
    fun getSearchHistoryStatus(): Boolean{
        return sharedPref.getBoolean(KEY_SEARCH_HISTORY_STATUS, true)
    }
}