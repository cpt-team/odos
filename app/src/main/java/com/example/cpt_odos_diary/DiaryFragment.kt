package com.example.cpt_odos_diary

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class DiaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_diary, container, false)

        val diaryButton = view.findViewById<Button>(R.id.button3)



        diaryButton.setOnClickListener {
            val diaryIntent = Intent(requireContext(), DiaryEditActivity::class.java)
            startActivity(diaryIntent)
        }

        return view
    }
}