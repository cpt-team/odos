package com.example.cpt_odos_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PlantSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_select)

        val selectbutton = findViewById<Button>(R.id.selectbutton)
        selectbutton.setOnClickListener {
            val selectintent = Intent(this, MainActivity::class.java)
            startActivity(selectintent)
        }
    }
}