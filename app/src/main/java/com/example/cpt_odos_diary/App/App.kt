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
        token_prefs.diaryCnt = "0"
        token_prefs.odosCnt = "0"
        token_prefs.emotionList = listOf()
        token_prefs.whetherList = listOf()
        token_prefs.plantName = ""
        token_prefs.plantDesc = ""
        token_prefs.plantLevel = 0
        token_prefs.podSkin = "0"
        token_prefs.backSkin = "0"



        // 앱실행시 token 지우기. signOut만들면 그 때 이거 쓰자
        token_prefs.editor.remove("accessToken")
        token_prefs.editor.commit()

        if(token_prefs.accessToken.isNullOrBlank()){
            val intent = Intent(this, SigninActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }
}
