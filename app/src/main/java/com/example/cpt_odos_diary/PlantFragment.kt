package com.example.cpt_odos_diary

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cpt_odos_diary.databinding.FragmentPlantBinding

class PlantFragment : Fragment() {
    lateinit var binding: FragmentPlantBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlantBinding.inflate(layoutInflater)

        // 화분 꾸미기 버튼
        val plantButton = binding.plantEdit

        // 화분 꾸미기 버튼을 눌렀을 때
        plantButton.setOnClickListener {
            // 인텐트로 액티비티 전환
            val guideIntent = Intent(requireContext(), PlantinfoActivity::class.java)
            startActivity(guideIntent)
        }

        binding.test.setOnClickListener{
            val intent = Intent(requireContext(), PlantChoiceActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }
}