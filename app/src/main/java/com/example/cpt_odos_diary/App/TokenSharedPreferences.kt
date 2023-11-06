package com.example.cpt_odos_diary.App

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class TokenSharedPreferences(context: Context) {
    private val prefsFilename = "token_prefs"
    private val key_accessToken = "accessToken"
    private val key_uid = "uid"

    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)
    val editor : SharedPreferences.Editor = prefs.edit()

    var accessToken: String?
        get() = prefs.getString(key_accessToken, "")
        set(value) = prefs.edit().putString(key_accessToken, value).apply()

    var uid: String?
        get() = prefs.getString(key_uid, "")
        set(value) = prefs.edit().putString(key_uid, value).apply()
}