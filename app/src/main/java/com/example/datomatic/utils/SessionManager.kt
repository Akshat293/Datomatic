package com.example.datomatic.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.datomatic.utils.Constants.Companion.DOCTOR_ID
import com.example.datomatic.utils.Constants.Companion.PREFS_TOKEN_FILE
import com.example.datomatic.utils.Constants.Companion.PRESCRIPTION_ID
import com.example.datomatic.utils.Constants.Companion.USER_TOKEN

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun saveDocId(token: String) {
        val editor = prefs.edit()
        editor.putString(DOCTOR_ID, token)
        editor.apply()
    }
    fun savePrecId(token: String) {
        val editor = prefs.edit()
        editor.putString(PRESCRIPTION_ID, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
    fun getDocid(): String? {
        return prefs.getString(DOCTOR_ID, null)
    }
    fun getPrescId(): String? {
        return prefs.getString(PRESCRIPTION_ID, null)
    }
}