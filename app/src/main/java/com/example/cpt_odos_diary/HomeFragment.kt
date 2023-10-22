package com.example.cpt_odos_diary

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton


class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val achieveButton = view.findViewById<ImageButton>(R.id.achievementButton)
        val settingButton = view.findViewById<ImageButton>(R.id.settingsButton)
        val alarmButton = view.findViewById<ImageButton>(R.id.alarmButton)
        // "업적" 버튼을 클릭했을 때 AchieveActivity로 이동
        achieveButton.setOnClickListener {
            val achieveIntent = Intent(requireContext(), AchieveActivity::class.java)
            startActivity(achieveIntent)
        }

        // "설정" 버튼을 클릭했을 때 SettingActivity로 이동
        settingButton.setOnClickListener {
            val settingIntent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(settingIntent)
        }

        // "알람" 버튼을 클릭했을 때 AlarmActivity로 이동
        alarmButton.setOnClickListener {
            val alarmIntent = Intent(requireContext(), AlarmActivity::class.java)
            startActivity(alarmIntent)
        }

        return view
    }
}