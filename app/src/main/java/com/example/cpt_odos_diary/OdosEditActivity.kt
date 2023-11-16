package com.example.cpt_odos_diary

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView


class OdosEditActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_odos_edit)

        val backbtn = findViewById<ImageView>(R.id.iv_odosback)
        backbtn.setOnClickListener {
            finish()
        }
    }
}
