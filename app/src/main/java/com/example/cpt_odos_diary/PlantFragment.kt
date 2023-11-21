package com.example.cpt_odos_diary

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cpt_odos_diary.App.App
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

    override fun onResume() {
        super.onResume()

        val plant = binding.plant
        val background = binding.background
        val pod = binding.pod
        val plantName = binding.plantName
        val floriographyDesc = binding.floriographyDesc

        Log.d(TAG,"plant Data: ${App.token_prefs.plantName}")
        Log.d(TAG,"plant Data: ${App.token_prefs.plantDesc}")
        Log.d(TAG,"plant Data: ${App.token_prefs.floriography}")


        plant.setImageResource(R.drawable.gypsophila_purple1)
        background.setImageResource(R.drawable.basicback)
        pod.setImageResource(R.drawable.basicpod)
        plantName.text = App.token_prefs.plantName
        floriographyDesc.text = App.token_prefs.floriography





    }




}