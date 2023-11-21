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

        Log.d(TAG,"plant Data - name: ${App.token_prefs.plantName}")
        Log.d(TAG,"plant Data - desc: ${App.token_prefs.plantDesc}")
        Log.d(TAG,"plant Data - flo: ${App.token_prefs.floriography}")
        Log.d(TAG,"plant Data - pod: ${App.token_prefs.podSkin}")
        Log.d(TAG,"plant Data - back: ${App.token_prefs.backSkin}")
        Log.d(TAG,"plant Data - level: ${App.token_prefs.plantLevel}")

        when(App.token_prefs.plantName){
            "안개꽃" -> plant.setImageResource(R.drawable.gypsophila_purple1)
            "튤립" -> plant.setImageResource(R.drawable.tulip2)
            "장미" -> plant.setImageResource(R.drawable.chrysanthemum1)
        }
        when(App.token_prefs.podSkin){
            "basicpod" -> pod.setImageResource(R.drawable.basicpod)
            "greenpod" -> pod.setImageResource(R.drawable.greenpod)
            "pinkpod" -> pod.setImageResource(R.drawable.pinkpod)
            "smilepod" -> pod.setImageResource(R.drawable.smilepod)
            "yellowpod" -> pod.setImageResource(R.drawable.yellowpod)
        }
        when(App.token_prefs.backSkin){
            "basicpod" -> background.setImageResource(R.drawable.basicback)
            "desert1" -> background.setImageResource(R.drawable.desert1)
            "desert2" -> background.setImageResource(R.drawable.desert2)
            "desert3" -> background.setImageResource(R.drawable.desert3)
            "field1" -> background.setImageResource(R.drawable.field1)
            "field2" -> background.setImageResource(R.drawable.field2)
            "field4" -> background.setImageResource(R.drawable.field4)
            "field5" -> background.setImageResource(R.drawable.field5)
            "snow1" -> background.setImageResource(R.drawable.snow1)
            "snow2" -> background.setImageResource(R.drawable.snow2)
            "snow3" -> background.setImageResource(R.drawable.snow3)
            "snow4" -> background.setImageResource(R.drawable.snow4)
            "snow5" -> background.setImageResource(R.drawable.snow5)
            "snow6" -> background.setImageResource(R.drawable.snow6)
            "snow7" -> background.setImageResource(R.drawable.snow7)
            "snow8" -> background.setImageResource(R.drawable.snow8)

        }

        plantName.text = App.token_prefs.plantName
        floriographyDesc.text = App.token_prefs.floriography





    }




}