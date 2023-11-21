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
    private val key_plantName = "plantName"
    private val key_floriography = "floriography"
    private val key_plantDesc = "plantDesc"
    private val key_podSkin = "basicPod"
    private val key_backSckin = "basicBack"
    private val key_plantLevel = "plantLevel"
    private val key_dayList = "dayList"


    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)
    val editor : SharedPreferences.Editor = prefs.edit()


    var dayList : List<String>?
        get() {
            val dayListString = prefs.getString(key_dayList,"")
            return stringToList(dayListString?: "")
        }
        set(value) {
            val dayListString = value?.let { listToString(it) }
            prefs.edit().putString(key_dayList, dayListString).apply()
        }
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


    var plantName : String?
        get() = prefs.getString(key_plantName, "")
        set(value) = prefs.edit().putString(key_plantName, value).apply()

    var floriography : String?
        get() = prefs.getString(key_floriography, "")
        set(value) = prefs.edit().putString(key_floriography, value).apply()

    var plantDesc : String?
        get() = prefs.getString(key_plantDesc, "")
        set(value) = prefs.edit().putString(key_plantDesc, value).apply()

    var podSkin : String?
        get() = prefs.getString(key_podSkin, "")
        set(value) = prefs.edit().putString(key_podSkin, value).apply()

    var backSkin : String?
        get() = prefs.getString(key_backSckin, "")
        set(value) = prefs.edit().putString(key_backSckin, value).apply()

    var plantLevel : Int?
        get() = prefs.getInt(key_plantLevel, 1)
        set(value) = prefs.edit().putInt(key_plantLevel, value?:1).apply()

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