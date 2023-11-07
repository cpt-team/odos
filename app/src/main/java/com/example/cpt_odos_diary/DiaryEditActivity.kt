package com.example.cpt_odos_diary

import android.content.ContentValues.TAG
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import java.util.Locale

class DiaryEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_edit)

        val dateTextView = findViewById<TextView>(R.id.dateTextView)

        // Intent에서 선택한 날짜 정보를 추출
        val selectedDateMillis = intent.getLongExtra("selectedDate", 0)

        if (selectedDateMillis > 0) {
            val selectedDate = Calendar.getInstance()
            selectedDate.timeInMillis = selectedDateMillis

            // 선택한 날짜를 TextView에 표시
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            dateTextView.text = formattedDate

            Log.d(TAG,"date: "+formattedDate) // 받은 날짜 보여줌.
        }

        val backbtn = findViewById<ImageView>(R.id.iv_back)
        backbtn.setOnClickListener {
            val diaryFragment = DiaryFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.diary, diaryFragment)
                .commit()
        }
    }
}