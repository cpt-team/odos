package com.example.cpt_odos_diary

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.cpt_odos_diary.databinding.ActivityDiaryEditBinding
import com.example.cpt_odos_diary.databinding.ActivityOdosEditBinding


class OdosEditActivity : AppCompatActivity() {
    lateinit var binding : ActivityOdosEditBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOdosEditBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val backbtn = binding.ivOdosback
        backbtn.setOnClickListener {
            finish()
        }


    }
}
