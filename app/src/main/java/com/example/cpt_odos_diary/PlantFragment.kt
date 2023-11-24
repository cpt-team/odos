package com.example.cpt_odos_diary

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.cpt_odos_diary.App.App
import com.example.cpt_odos_diary.databinding.FragmentPlantBinding

class PlantFragment : Fragment() {
    lateinit var binding: FragmentPlantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantBinding.inflate(layoutInflater)

        // 툴바
        (activity as AppCompatActivity).setSupportActionBar(binding.plantEdit1)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        // 식물 선택 화면 버튼
        binding.test.setOnClickListener {
            val intent = Intent(requireContext(), PlantChoiceActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    // 툴바
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.plant_edit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // 툴바 아이콘 눌렀을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val guideIntent = Intent(requireContext(), PlantinfoActivity::class.java)
        startActivity(guideIntent)
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        val plant = binding.plant
        val background = binding.background
        val pod = binding.pod
        val plantName = binding.plantName
        val floriographyDesc = binding.floriographyDesc
        val plantWater = binding.plantWater


        Log.d(TAG, "plant Data - name: ${App.token_prefs.plantName}")
        Log.d(TAG, "plant Data - desc: ${App.token_prefs.plantDesc}")
        Log.d(TAG, "plant Data - flo: ${App.token_prefs.floriography}")
        Log.d(TAG, "plant Data - pod: ${App.token_prefs.podSkin}")
        Log.d(TAG, "plant Data - back: ${App.token_prefs.backSkin}")
        Log.d(TAG, "plant Data - level: ${App.token_prefs.plantLevel}")

        when (App.token_prefs.plantName) {
            "안개꽃" -> plant.setImageResource(R.drawable.gypsophila_purple2)
            "튤립" -> plant.setImageResource(R.drawable.tulip2)
            "장미" -> plant.setImageResource(R.drawable.chrysanthemum2)
        }
        when (App.token_prefs.podSkin) {
            "basicpod" -> pod.setImageResource(R.drawable.basicpod)
            "greenpod" -> pod.setImageResource(R.drawable.greenpod)
            "pinkpod" -> pod.setImageResource(R.drawable.pinkpod)
            "smilepod" -> pod.setImageResource(R.drawable.smilepod)
            "yellowpod" -> pod.setImageResource(R.drawable.yellowpod)
        }
        when (App.token_prefs.backSkin) {
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

        val diaryCnt = App.token_prefs.diaryCnt
        val odosCnt = App.token_prefs.odosnt

        if((diaryCnt!! + odosCnt!!) == 4){
            App.token_prefs.plantLevel = 2
        }

        if((diaryCnt + odosCnt) == 5){
            App.token_prefs.plantLevel = 3
        }

        if((diaryCnt + odosCnt) == 6){
            App.token_prefs.plantLevel = 4
        }


        if (plantName.text == "안개꽃") {
            when (App.token_prefs.plantLevel) {
                2 -> plant.setImageResource(R.drawable.gypsophila_purple3)
                3 -> plant.setImageResource(R.drawable.gypsophila_purple4)
                4 -> plant.setImageResource(R.drawable.gypsophila_purple5)

            }
            if (plantName.text == "튤립") {
                when (App.token_prefs.plantLevel) {
                    2 -> plant.setImageResource(R.drawable.tulip3)
                    3 -> plant.setImageResource(R.drawable.tulip4)
                    4 -> plant.setImageResource(R.drawable.tulip5)

                }
            }
            if (plantName.text == "국화") {
                when (App.token_prefs.plantLevel) {
                    2 -> plant.setImageResource(R.drawable.chrysanthemum3)
                    3 -> plant.setImageResource(R.drawable.chrysanthemum4)
                    4 -> plant.setImageResource(R.drawable.chrysanthemum5)

                }
            }



            plantName.text = App.token_prefs.plantName
            plantWater.text ="${diaryCnt + odosCnt}회"
            floriographyDesc.text = App.token_prefs.floriography

        }
    }
}