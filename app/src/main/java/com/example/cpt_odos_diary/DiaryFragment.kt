package com.example.cpt_odos_diary

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView


class DiaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary, container, false)

        val calendarView = view.findViewById<CalendarView>(R.id.calendarview)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // 클릭한 날짜 정보를 가져오기
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            // DiaryEditActivity로 이동하는 Intent를 생성
            val intent = Intent(activity, DiaryEditActivity::class.java)

            // 날짜 정보를 Intent에 추가
            intent.putExtra("selectedDate", selectedDate.timeInMillis)

            // DiaryEditActivity 시작
            startActivity(intent)
        }

        return view
    }
}