package com.example.cpt_odos_diary.App

import android.app.Application
import android.content.Intent
import com.example.cpt_odos_diary.MainActivity
import com.example.cpt_odos_diary.SigninActivity

class App: Application() {
    companion object{
        lateinit var token_prefs : TokenSharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        token_prefs = TokenSharedPreferences(applicationContext)

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