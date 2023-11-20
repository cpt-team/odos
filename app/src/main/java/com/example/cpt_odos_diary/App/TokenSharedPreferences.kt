package com.example.cpt_odos_diary.App

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class TokenSharedPreferences(context: Context) {
    private val prefsFilename = "token_prefs"
    private val key_accessToken = "accessToken"
    private val key_uid = "uid"
    private val key_odosCnt = "odosCnt"
    private val key_diaryCnt = "diaryCnt"
    private val key_emotionList = "emotionList"
    private val key_whetherList = "whetherList"


    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)
    val editor : SharedPreferences.Editor = prefs.edit()


    var emotionList : List<String>?
        get() {
            val emotionListString = prefs.getString(key_emotionList,"")
            return stringToList(emotionListString?: "")
        }
        set(value) {
            val odosListString = value?.let { listToString(it) }
            prefs.edit().putString(key_emotionList, odosListString).apply()
        }
    var whetherList : List<String>?
        get() {
            val whetherListString = prefs.getString(key_whetherList,"")
            return stringToList(whetherListString?: "")
        }
        set(value) {
            val odosListString = value?.let { listToString(it) }
            prefs.edit().putString(key_whetherList, odosListString).apply()
        }


    var odosCnt : String?
        get() = prefs.getString(key_odosCnt, "")
        set(value) = prefs.edit().putString(key_odosCnt, value).apply()
    var diaryCnt : String?
        get() = prefs.getString(key_diaryCnt, "")
        set(value) = prefs.edit().putString(key_diaryCnt, value).apply()

    var accessToken: String?
        get() = prefs.getString(key_accessToken, "")
        set(value) = prefs.edit().putString(key_accessToken, value).apply()

    var uid: String?
        get() = prefs.getString(key_uid, "")
        set(value) = prefs.edit().putString(key_uid, value).apply()
}

private fun listToString(list: List<String>): String {
    return list.joinToString(separator = ",")
}

private fun stringToList(str: String): List<String> {
    return str.split(",").map { it.trim() }
}