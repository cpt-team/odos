package com.example.cpt_odos_diary.App

import android.app.Application
import android.content.Intent
import com.example.cpt_odos_diary.MainActivity
import com.example.cpt_odos_diary.SigninActivity

class App: Application() {
    companion object{
        lateinit var token_prefs : TokenSharedPreferences
        // 안드로이드 기기자체에 데이터를 저장해놓는거임.
        // 모든 뷰에서 쓸 수 있음
    }
    override fun onCreate() {
        super.onCreate()
        token_prefs = TokenSharedPreferences(applicationContext)
        token_prefs.emotionList = listOf()
        token_prefs.whetherList = listOf()
        token_prefs.uid = "?"
        token_prefs.plantName = "?"
        token_prefs.plantDesc = ""
        token_prefs.plantLevel = 0
        token_prefs.podSkin = "0"
        token_prefs.backSkin = "0"
        token_prefs.odosYear = 1
        token_prefs.odosMonth = 1
        token_prefs.checkToggle = ""
        token_prefs.diaryCnt = 0
        token_prefs.odosnt = 0

        token_prefs.backSkin = "basicback"
        token_prefs.podSkin = "basicpod"


        // 앱실행시 token 지우기. signOut만들면 그 때 이거 쓰자


        /*
        if(uidCheck == "?"){
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, SigninActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }

         */



    }
}
