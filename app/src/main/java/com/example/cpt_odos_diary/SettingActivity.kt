package com.example.cpt_odos_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.cpt_odos_diary.databinding.ActivitySignupBinding
import java.util.Date

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)

        findViewById<ImageView>(R.id.iv_settingback).setOnClickListener{
            finish()
        }
    }
}