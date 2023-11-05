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

        val btnOpenOdosFragment = findViewById<ImageView>(R.id.iv_odosback)
        btnOpenOdosFragment.setOnClickListener {
            // OdosFragment로 이동
            val odosFragment = OdosFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.odosfragment, odosFragment)
                .addToBackStack(null) // 뒤로가기 스택에 추가
                .commit()
        }
    }
}
